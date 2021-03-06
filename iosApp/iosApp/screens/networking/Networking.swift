//
//  Networking.swift
//  iosApp
//
//  Created by GERARD on 19/02/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct Networking: View {
    var networkingUi: NetworkingUi
    var onValidation: (String, String, String, String) -> ()
    var onDismissProfileSheet: () -> ()
    var onNetworkDeleted: (String) -> ()

    var body: some View {
        Group {
            if (networkingUi.users.count > 0) {
                ScrollView {
                    LazyVStack(spacing: 10) {
                        ForEach(networkingUi.users, id: \.self) { user in
                            UserItemView(
                                user: user,
                                onNetworkDeleted: onNetworkDeleted
                            )
                            .padding()
                            .frame(maxWidth: .infinity, alignment: .leading)
                        }
                    }
                }
            } else {
                Text("textNetworkingNoScan")
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 30)
            }
        }
        .sheet(isPresented: Binding.constant(networkingUi.showQrCode), onDismiss: {
            onDismissProfileSheet()
        }) {
            ProfileInputView(
                email: networkingUi.userProfileUi.email,
                firstName: networkingUi.userProfileUi.firstName,
                lastName: networkingUi.userProfileUi.lastName,
                company: networkingUi.userProfileUi.company,
                qrCode: networkingUi.userProfileUi.qrCode,
                onValidation: onValidation
            )
        }
    }
}

struct Networking_Previews: PreviewProvider {
    static var previews: some View {
        Networking(
            networkingUi: NetworkingUi.companion.fake,
            onValidation: { _, _, _, _ in },
            onDismissProfileSheet: {},
            onNetworkDeleted: { _ in }
        )
    }
}
