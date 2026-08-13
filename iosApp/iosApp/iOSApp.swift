import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinModulesKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
