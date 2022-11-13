//
//  ScheduleItemViewModel.swift
//  iosApp
//
//  Created by GERARD on 15/02/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

enum ScheduleUiState {
    case loading
    case success(TalkUi)
}

@MainActor
class ScheduleItemViewModel: ObservableObject {
    let repository: AgendaRepository

    init(repository: AgendaRepository) {
        self.repository = repository
    }

    @Published var uiState: ScheduleUiState = .loading

    func fetchScheduleDetails(scheduleId: String) {
        let talk = repository.scheduleItem(scheduleId: scheduleId)
        self.uiState = .success(talk)
    }
}
