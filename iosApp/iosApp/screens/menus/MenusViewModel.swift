//
//  MenusViewModel.swift
//  iosApp
//
//  Created by GERARD on 22/05/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SharedDi
import KMPNativeCoroutinesAsync

enum MenusUiState {
    case loading
    case success(Array<MenuItemUi>)
    case failure
}

@MainActor
class MenusViewModel: ObservableObject {
    private let repository: AgendaRepository = RepositoryHelper().agendaRepository

    @Published var uiState: MenusUiState = MenusUiState.loading

    private var menusTask: Task<(), Never>?

    func fetchMenus() {
        menusTask = Task {
            do {
                let stream = asyncStream(for: repository.menusNative())
                for try await menus in stream {
                    self.uiState = MenusUiState.success(menus)
                }
            } catch {
                self.uiState = MenusUiState.failure
            }
        }
    }

    func stop() {
        menusTask?.cancel()
    }
}
