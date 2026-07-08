// swift-tools-version:5.3
// Wrapper package for CometChatCardsSwift, which CometChat publishes as a
// CocoaPods-only binary. UI Kit 5.1.16's binary imports this module (and
// CometChatStarscream, vended by CometChatSDK >= 4.1.6), so SPM consumers must
// supply it explicitly. Version 1.1.0 matches UI Kit 5.1.16.

import PackageDescription

let package = Package(
    name: "CometChatCardsSwift",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(name: "CometChatCardsSwift", targets: ["CometChatCardsSwift"])
    ],
    targets: [
        .binaryTarget(
            name: "CometChatCardsSwift",
            url: "https://dl.cloudsmith.io/public/cometchat/cometchat/raw/versions/1.1.0/CometChatCardsSwift-1.1.0.zip",
            checksum: "84ba681903e1d7ae27c669a6d691da087732eb75437cdcdd4862919046653637"
        )
    ]
)
