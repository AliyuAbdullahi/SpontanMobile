//
//  EventsListScreen.swift
//  iosApp
//
//  Created by Aliyu Olalekan on 06/06/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct EventRow: View {
    var event: EventData
    
    var body: some View {
        VStack {
            Spacer()
            Text(event.title)
                .frame(width: 380, alignment: .leading)
                .font(Font.headline.weight(.bold))
                .padding(-8)
            Spacer()
            Text(event.date)
                .frame(width: 380, alignment: .leading)
                .padding()
        }.padding(.horizontal)
            .fixedSize(horizontal: false, vertical: true)
    }
}

struct EventsListScreen: View {
    
    @ObservedObject var viewModel : EventListViewModel = EventListViewModel()
    
    @State
    var events: [EventDomainData] = []
    
    var body: some View {
        if (viewModel.currentState.isLoading) {
            LoadingView()
        } else {
            List(viewModel.currentState.events) { event in
                EventRow(event: event)
            }
        }
    }
}

struct EventsListScreen_Previews: PreviewProvider {
    static var previews: some View {
        EventsListScreen()
    }
}
