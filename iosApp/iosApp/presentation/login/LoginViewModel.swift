//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Aliyu Olalekan on 28/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct LoginViewState {
    var name: String = ""
    var email: String = ""
    var password: String = ""
    var isLoading: Bool = false
    var error: String = ""
    var isLoginSuccess: Bool = false
    var accessToken: String = ""
    var userAlreadyIn: Bool = false
    var isValid: Bool {
        return !email.isEmpty && !password.isEmpty
    }
}

extension LoginViewState {
        
    func copy(
        name: String? = nil,
        email: String? = nil,
        password: String? = nil,
        isLoading: Bool? = nil,
        error: String? = nil,
        isLoginSuccess: Bool? = nil,
        accessToken: String? = nil,
        userAlreadyIn: Bool? = nil
    ) -> LoginViewState {
        LoginViewState(
            name: name ?? self.name,
            email: email ?? self.email,
            password: password ?? self.password,
            isLoading: isLoading ?? self.isLoading,
            error: error ?? self.error,
            isLoginSuccess: isLoginSuccess ?? self.isLoginSuccess,
            accessToken: accessToken ?? self.accessToken,
            userAlreadyIn: userAlreadyIn ?? self.userAlreadyIn
        )
    }
}

struct LoginModel {
    var email: String
    var password: String
}

struct LoginDomainData {
    let email: String
    let name: String
    let accessToken: String
    let error: String
}

protocol ILoginUseCase {
    func invoke(requestModel: LoginModel, handler: @escaping (LoginDomainData?, Error?) -> Void)
}

extension LoginResponseDomainData {
    func toDomainResponse() -> LoginDomainData {
        return LoginDomainData(email: self.email, name: self.name, accessToken: self.accessToken, error: self.error)
    }
}

class LoginUseCaseImp : ILoginUseCase {
    private let useCase: LoginUseCase = DI.shared.loginUseCase
    
    func invoke(requestModel: LoginModel, handler: @escaping (LoginDomainData?, Error?) -> Void) {
        useCase.invoke(requestModel: LoginRequestModel(email: requestModel.email, password: requestModel.password)){result, error in
            if let result = result {
                handler(result.toDomainResponse(), nil)
            } else if let error = error {
                handler(nil, error)
            }
        }
    }
}

class LoginViewModel : ObservableObject {
    private var useCase: ILoginUseCase
    private var addUserUseCase: AddUserUseCase
    private var getUserUseCase: GetUserUseCase
    
    init(useCase: ILoginUseCase, addUserUseCase: AddUserUseCase, getUserUseCase: GetUserUseCase) {
        self.useCase = LoginUseCaseImp()
        self.addUserUseCase = addUserUseCase
        self.getUserUseCase = getUserUseCase
        
        getUserUseCase.invoke { result, error in
            if let result = result {
                self.setState(newState: self.currentState.copy(userAlreadyIn : result.accessToken != nil))
            } else if let error = error {
                print(error.localizedDescription)
            }
        }
    }
    
    convenience init() {
        self.init(useCase: LoginUseCaseImp(), addUserUseCase: DI.shared.addUserUseCase, getUserUseCase: DI.shared.getUserUseCase)
    }
    
    @Published
    var currentState: LoginViewState = LoginViewState()
    
    func setState(newState: LoginViewState) {
        self.currentState = newState
    }
    
    func performLogin() {
        do {
            try login { self.saveUSer() }
        } catch {
            setState(newState: self.currentState.copy(error: "Failed to login, check your input"))
        }
    }
    
    func saveUSer() {
        let user = UserDomainModel.init(name: currentState.name, email: currentState.email, accessToken: currentState.accessToken, photo: nil)
        addUserUseCase.invoke(user: user){ result, error in
            if result != nil {
                self.setState(newState: self.currentState.copy(userAlreadyIn : true))
            } else if let error = error {
                print(error.localizedDescription)
            }
        }
    }
    
    
    func clearError() {
        self.setState(newState: self.currentState.copy(error : ""))
    }
    
    private func login(onSuccess : @escaping () -> Void) throws {
        let newState = self.currentState.copy(isLoading: true, error: "")
        setState(newState: newState)
        let loginRequestModel = LoginModel(email: currentState.email, password: currentState.password)
        
        useCase.invoke(requestModel: loginRequestModel) { result, error in
            if let result = result {
                self.setState(newState: self.currentState.copy(name: result.name, isLoading: false, error: result.error, isLoginSuccess: true, accessToken: result.accessToken))
                onSuccess()
            } else if let error = error {
                self.setState(newState: self.currentState.copy(isLoading: false, error: error.localizedDescription))
            }
        }
    }
}
