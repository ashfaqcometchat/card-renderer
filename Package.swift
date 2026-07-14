// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "CometChatUIKitSwift",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(name: "CometChatUIKitSwift", targets: ["CometChatUIKitSwift"])
    ],
    targets: [
        .binaryTarget(
            name: "CometChatUIKitSwift",
            url: "https://dl.cloudsmith.io/public/cometchat/call-team/raw/versions/5.1.16-citest.5/CometChatUIKitSwift_5.1.16-citest.5.xcframework.zip",
            checksum: "d2bbd8f4dbc9fb97d528dba69b660c801c8c9b3f7e7166cd37d83e5a39987bb4"
        )
    ]
)
