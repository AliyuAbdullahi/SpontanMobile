//
//  LoginViewModelTest.swift
//  iosAppTests
//
//  Created by Aliyu Olalekan on 31/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import XCTest
@testable import iosApp

class LoginUsecasSuccessFake : ILoginUseCase {
    func invoke(requestModel: LoginModel, handler: @escaping (LoginDomainData?, Error?) -> Void) {
        handler(LoginDomainData.init(email: "testEmail", name: "testName", accessToken: "testToken"), nil)
    }
}

class LoginViewModelTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }
    
    func testLoginViewModel() throws {
        let viewModel = LoginViewModel.init(useCase: LoginUsecasSuccessFake())
        viewModel.performLogin()
        
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
