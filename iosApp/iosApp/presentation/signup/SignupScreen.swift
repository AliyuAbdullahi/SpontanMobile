//
//  SignupScreen.swift
//  iosApp
//
//  Created by Aliyu Olalekan on 28/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct SignupScreen: View {
    
    @ObservedObject var viewModel: SignupViewModel = SignupViewModel()
    
    fileprivate func SignupView() -> some View {
        return NavigationView {
            ZStack {
                if viewModel.currentState.isLoading {
                    LoadingView()
                } else {
                    VStack {
                        TextField("Name", text: $viewModel.currentState.name)
                            .padding(8)
                            .frame(width: 300, height: 50)
                            .textFieldStyle(.roundedBorder)
                            .cornerRadius(10)
                        
                        TextField("Email", text: $viewModel.currentState.email)
                            .padding(8)
                            .frame(width: 300, height: 50)
                            .textFieldStyle(.roundedBorder)
                            .cornerRadius(10)
                        
                        SecureField("Password", text: $viewModel.currentState.password)
                            .padding(8)
                            .frame(width: 300, height: 50)
                            .textFieldStyle(.roundedBorder)
                            .cornerRadius(6)
                            .cornerRadius(10)
                        
                        Button("Signup") {
                            if viewModel.currentState.isValid {
                                viewModel.performSignUp()
                            }
                        }.foregroundColor(.white)
                            .frame(width: 300, height: 50, alignment: Alignment.center)
                            .disabled(!viewModel.currentState.isValid)
                            .background(Color.orange)
                            .cornerRadius(10)
                        
                        NavigationLink(destination: LoginScreen(), label: {
                            Text("Already have account? Login")
                                .padding()
                        })
                        
                    }.navigationBarHidden(true)
                }
            }
        }.accentColor(Color(.label)).navigationBarHidden(true)
    }
    
    var body: some View {
        if viewModel.currentState.isSignupSuccess {
            SplashScreen()
        } else {
            SignupView()
        }
    }
}

struct SignupScreen_Previews: PreviewProvider {
    static var previews: some View {
        SignupScreen()
    }
}
