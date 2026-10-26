# 后续完善顺序

1. Android Studio 打开根目录，安装 SDK 35，Sync 后启动模拟器。
2. 检查 Feed、Albums、Favorites 三个底部入口；点专辑进入列表，收藏后重启 App 检查保存。
3. 点 Play 验证演示音频；它是生成的提示旋律，不是真实歌曲。
4. 启动 mock-api，点 Refresh mock API 验证 Retrofit 请求；真机需要改调试 API 地址。
5. 补封面、进度条、播放队列、下一首、搜索和完整错误/空状态。
6. 需要后台播放时再加入 MediaSessionService、通知、音频焦点与生命周期测试。
7. 接合法音源、完善 Room migration 和测试，再录制演示。

当前框架基于截图中的技术栈；尚未读取完整 Android 课程教案或导入你已有源码。
