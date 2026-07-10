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
            url: "https://dl.cloudsmith.io/public/cometchat/call-team/raw/versions/5.1.16-citest.2/CometChatUIKitSwift_5.1.16-citest.2.xcframework.zip",
            checksum: "7484702e0b512267dfb84bfe2985972b6e886531faf1eb68c2b2cf1df2ac9db9"
        )
    ]
)
