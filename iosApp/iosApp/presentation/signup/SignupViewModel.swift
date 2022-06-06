//
//  SignupViewModel.swift
//  iosApp
//
//  Created by Aliyu Olalekan on 28/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct SignupViewState {
    var name: String = ""
    var email: String = ""
    var password: String = ""
    var isLoading: Bool = false
    var isSignupSuccess: Bool = false
    var accessToken: String = ""
    var error: String = ""
    var userAlreadyIn: Bool = false
    
    var isValid: Bool {
        return !name.isEmpty && !email.isEmpty && !password.isEmpty
    }
    
    var hasError: Bool {
        return !error.isEmpty
    }
}

extension SignupViewState {
    
    func copy(
        name: String? = nil,
        email: String? = nil,
        password: String? = nil,
        isLoading: Bool? = nil,
        isSignupSuccess: Bool? = nil,
        accessToken: String? = nil,
        error: String? = nil,
        userAlreadyIn: Bool? = nil
    ) -> SignupViewState {
        return SignupViewState(
            name:  name ?? self.name,
            email: email ?? self.email,
            password: password ?? self.password,
            isLoading: isLoading ?? self.isLoading,
            isSignupSuccess: isSignupSuccess ?? self.isSignupSuccess,
            accessToken: accessToken ?? self.accessToken,
            error: error ?? self.error,
            userAlreadyIn: userAlreadyIn ?? self.userAlreadyIn
        )
    }
}

class SignupViewModel : ObservableObject {
    private var signupUseCase: SignUpUseCase
    private var getUserUseCase: GetUserUseCase
    private var addUserUseCase: AddUserUseCase
    
    
    init(useCase: SignUpUseCase, getUserUseCase: GetUserUseCase, addUserUseCase: AddUserUseCase) {
        self.signupUseCase = useCase
        self.getUserUseCase = getUserUseCase
        self.addUserUseCase = addUserUseCase
        
        getUserUseCase.invoke { result, error in
            if let result = result {
                self.setState(newState: self.currentState.copy(userAlreadyIn : result.accessToken != nil))
            } else if let error = error {
                print(error.localizedDescription)
            }
        }
    }
    
    convenience init() {
        self.init(useCase: DI.shared.signupUseCase, getUserUseCase: DI.shared.getUserUseCase, addUserUseCase: DI.shared.addUserUseCase)
    }
    
    @Published var currentState : SignupViewState = SignupViewState()
    
    func setState(newState: SignupViewState) {
        self.currentState = newState
    }
    
    
    func performSignUp() {
        do {
            try signup { self.saveUSer() }
        } catch {
            setState(newState: self.currentState.copy(error: "Signup failed check your input"))
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
    
    private func signup(onSuccess: @escaping () -> Void) throws {
        let newState = self.currentState.copy(isLoading: true, error: "")
        setState(newState: newState)
        let model = SignUpRequestModel(name: currentState.name, email: currentState.email, password: currentState.password)
        signupUseCase.invoke(requestModel: model){ result, error in
            if let result = result {
                self.setState(newState: self.currentState.copy(isSignupSuccess: true, accessToken: result.accessToken, error: ""))
                onSuccess()
            } else if let error = error {
                self.setState(newState: self.currentState.copy(error: error.localizedDescription))
            }
        }
    }
}
