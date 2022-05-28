import SwiftUI
import shared

struct ContentView: View {
    
	var greet = Greeting().greeting()
    
    @State
    var message: String = "Loading"

	var body: some View {
        Text(message).onAppear(){
            login()
        }
	}
    
    func login() {
        ServiceLocator.shared.loginUseCase.invoke(
            requestModel: LoginRequestModel(
                email: "jagun@gmail.com",
                password: "jagun"
            )
        ){ result, error in
            if let result = result {
                self.message = result.accessToken
            } else if let error = error {
                self.message = error.localizedDescription
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
