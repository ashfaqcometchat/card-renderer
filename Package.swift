// swift-tools-version:5.3

import PackageDescription

let package = Package(
    name: "CometChatCallsSDK",
    platforms: [
        .iOS("15.1")
    ],
    products: [
        .library(name: "CometChatCallsSDK", targets: ["CometChatCallsSDK", "WebRTC"])
    ],
    targets: [
        .binaryTarget(
            name: "CometChatCallsSDK",
            url: "https://dl.cloudsmith.io/public/cometchat/call-team/raw/versions/5.0.3-citest.2/CometChatCallsSDK-5.0.3-citest.2.zip",
            checksum: "37fe9632658410f4f3f6637f416d37c3d4af5dda18e87e6072dbeb125c6d7bbd"
        ),
        .binaryTarget(
            name: "WebRTC",
            url: "https://dl.cloudsmith.io/public/cometchat/cometchat/raw/versions/124.0.4/CometChatWebRTC-124.0.4.xcframework.zip",
            checksum: "fae7fc22b83c68ce69fcb5dbb447fe08ef2cc4b7a1ab83b624ae1bcc996d93fe"
        )
    ]
)
