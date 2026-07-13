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
            url: "https://dl.cloudsmith.io/public/cometchat/call-team/raw/versions/5.1.16-citest.3/CometChatUIKitSwift_5.1.16-citest.3.xcframework.zip",
            checksum: "7c0a862189040fdd900f0eea1e723d90466b49c5d33ba8e8d55096547e91a565"
        )
    ]
)
