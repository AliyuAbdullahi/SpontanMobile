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
    var email: String = ""
    var password: String = ""
    var isLoading: Bool = false
    var error: String = ""
    var isLoginSuccess: Bool = false
    var accessToken: String = ""
    
    var isValid: Bool {
        return !email.isEmpty && !password.isEmpty
    }
}

extension LoginViewState {
        
    func copy(
        email: String? = nil,
        password: String? = nil,
        isLoading: Bool? = nil,
        error: String? = nil,
        isLoginSuccess: Bool? = nil,
        accessToken: String? = nil
    ) -> LoginViewState {
        LoginViewState(
            email: email ?? self.email,
            password: password ?? self.password,
            isLoading: isLoading ?? self.isLoading,
            error: error ?? self.error,
            isLoginSuccess: isLoginSuccess ?? self.isLoginSuccess,
            accessToken: accessToken ?? self.accessToken
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
}

protocol ILoginUseCase {
    func invoke(requestModel: LoginModel, handler: @escaping (LoginDomainData?, Error?) -> Void)
}

extension LoginResponseDomainData {
    func toDomainResponse() -> LoginDomainData {
        return LoginDomainData(email: self.email, name: self.name, accessToken: self.accessToken)
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
    
    init(useCase: ILoginUseCase) {
        self.useCase = LoginUseCaseImp()
    }
    
    convenience init() {
        self.init(useCase: LoginUseCaseImp())
    }
    
    @Published
    var currentState: LoginViewState = LoginViewState()
    
    func setState(newState: LoginViewState) {
        self.currentState = newState
    }
    
    func performLogin() {
        do {
            try login()
        } catch {
            setState(newState: self.currentState.copy(error: "Failed to login, check your input"))
        }
    }
    
    private func login() throws {
        let newState = self.currentState.copy(isLoading: true, error: "")
        setState(newState: newState)
        let loginRequestModel = LoginModel(email: currentState.email, password: currentState.password)
        
        useCase.invoke(requestModel: loginRequestModel) { result, error in
            if let result = result {
                self.setState(newState: self.currentState.copy(isLoading: false, error: "", isLoginSuccess: true, accessToken: result.accessToken))
            } else if let error = error {
                self.setState(newState: self.currentState.copy(isLoading: false, error: error.localizedDescription))
            }
        }
    }
}