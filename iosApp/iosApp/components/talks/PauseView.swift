//
//  PauseView.swift
//  iosApp
//
//  Created by GERARD on 15/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PauseView: View {
    let title: String

    var body: some View {
        Text(title)
            .font(.title3)
    }
}

struct PauseView_Previews: PreviewProvider {
    static var previews: some View {
        PauseView(title: "Pause")
    }
}
