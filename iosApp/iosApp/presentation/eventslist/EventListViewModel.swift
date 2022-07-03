//
//  EventListViewModel.swift
//  iosApp
//
//  Created by Aliyu Olalekan on 03/07/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

struct EventData : Identifiable {
    var id: String = ""
    var title: String = ""
    var description: String = ""
    var date: String = ""
}

extension EventDomainData {
    private func getDate(dateData: DateData?, timeData: TimeData?) -> String {
        if let dateData = dateData {
            if let timeData = timeData {
                return "\(dateData.day)/\(dateData.month)/\(dateData.year) at \(timeData.hour) : \(timeData.minute)"
            } else {
                return  "\(dateData.day)/\(dateData.month)/\(dateData.year)"
            }
        } else {
            return ""
        }
    }
    func toEventData() -> EventData {
        return EventData(id: self.id, title: self.title, description: self.description(), date: self.getDate(dateData: self.dateData, timeData: self.timeData))
    }
}

struct EventListViewState {
    var isLoading: Bool = true
    var error: String = ""
    var events: [EventData] = []
}

class EventListViewModel : ObservableObject {
    
    private var loadEventsUseCase: LoadEventsUseCase
    
    init(useCase: LoadEventsUseCase) {
        self.loadEventsUseCase = useCase
    }
    
    convenience init() {
        self.init(useCase: DI.shared.loadEventsUseCase)
        loadEvents()
    }
    
    func loadEvents() {
        loadEventsUseCase.invoke { result, error in
            if let result = result {
                var currentEventDataList: [EventData] = []
                for item in result.data  {
                    currentEventDataList.append(item.toEventData())
                }
                self.currentState.events = currentEventDataList
                self.currentState.isLoading = false
                self.currentState.error = ""
            }
            
            if let error = error {
                self.currentState.error = error.localizedDescription
            }
        }
    }
    
    @Published
    var currentState: EventListViewState = EventListViewState()
}
