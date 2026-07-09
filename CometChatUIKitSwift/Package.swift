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
            url: "https://dl.cloudsmith.io/public/cometchat/call-team/raw/versions/5.1.16-citest.1/CometChatUIKitSwift_5.1.16-citest.1.xcframework.zip",
            checksum: "861119e5ddfac140760234349e7ef02d49423e69d9fc670fc349e7a1d8e4fe4a"
        )
    ]
)
