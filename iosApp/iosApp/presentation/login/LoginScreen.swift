//
//  LoginScreen.swift
//  iosApp
//
//  Created by Aliyu Olalekan on 28/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct LoginScreen: View {
    
    @ObservedObject var viewModel : LoginViewModel = LoginViewModel()
    
    fileprivate func LoginView() -> some View {
        return NavigationView {
            ZStack {
                if viewModel.currentState.isLoading {
                    LoadingView()
                } else {
                    VStack {
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
                        
                        Button("Login") {
                            viewModel.performLogin()
                        }.foregroundColor(.white)
                            .frame(width: 300, height: 50, alignment: Alignment.center)
                            .background(Color.orange)
                            .cornerRadius(10)
                        
                        NavigationLink(destination: SignupScreen(), label: {
                            Text("Don't have account? Signup")
                                .padding()
                        })
                        
                    }.navigationBarHidden(true)
                }
            }.navigationBarHidden(true)
        }.accentColor(Color(.label))
    }
    
    var body: some View {
        if viewModel.currentState.isLoginSuccess {
            SplashScreen()
        } else {
            LoginView()
        }
    }
}

struct LoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen()
    }
}
