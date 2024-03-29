//
//  AppViewModel.swift
//  iosApp
//
//  Created by GERARD on 16/02/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SharedDi
import KMPNativeCoroutinesAsync

@MainActor
class HomeViewModel: ObservableObject {
    private let repository: AgendaRepository = RepositoryHelper().agendaRepository

    private var task: Task<(), Never>?

    func fetchAgenda() {
        task = Task {
            do {
                let stream = asyncStream(for: repository.fetchAndStoreAgendaNative())
                for try await _ in stream {
                    // Nothing to do
                }
            } catch {
                // Ignore failure
            }
        }
    }

    func stop() {
        task?.cancel()
    }
}
