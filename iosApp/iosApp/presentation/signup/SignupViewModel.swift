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
        error: String? = nil
    ) -> SignupViewState {
        return SignupViewState(
            name:  name ?? self.name,
            email: email ?? self.email,
            password: password ?? self.password,
            isLoading: isLoading ?? self.isLoading,
            isSignupSuccess: isSignupSuccess ?? self.isSignupSuccess,
            accessToken: accessToken ?? self.accessToken,
            error: error ?? self.error
        )
    }
}

class SignupViewModel : ObservableObject {
    private var signupUseCase: SignUpUseCase
    
    
    init(useCase: SignUpUseCase) {
        self.signupUseCase = useCase
    }
    
    convenience init() {
        self.init(useCase: DI.shared.signupUseCase)
    }
    
    @Published var currentState : SignupViewState = SignupViewState()
    
    func setState(newState: SignupViewState) {
        self.currentState = newState
    }
    
    func performSignUp() {
        let model = SignUpRequestModel(name: currentState.name, email: currentState.email, password: currentState.password)
        signupUseCase.invoke(requestModel: model){ result, error in
            if let result = result {
                self.setState(newState: self.currentState.copy(isSignupSuccess: true, accessToken: result.accessToken, error: ""))
            } else if let error = error {
                self.setState(newState: self.currentState.copy(error: error.localizedDescription))
            }
        }
    }
}
