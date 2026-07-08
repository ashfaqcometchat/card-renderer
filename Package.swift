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
            url: "https://dl.cloudsmith.io/public/cometchat/call-team/raw/versions/5.1.16-citest.8/CometChatUIKitSwift_5.1.16-citest.8.xcframework.zip",
            checksum: "3ff3a01fdc0d2a586111befa38a8109507b4954ece9d88f02ee50026c233e538"
        )
    ]
)
