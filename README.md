# Eraser Client

### TODO
1. network/Receiver
2. graphics/*
3. TCP event handling
4. eraser/Game

### 連線概述

#### TCP
- 登入
  1. 建立TCP連線
  2. 玩家傳送 "NAME\nPORT\n" 給伺服器，PORT 是玩家的 UDP 接收端
  3. 伺服器回應玩家一個 "ID\n" 字串，收到之後客戶端開始遊戲
- 再玩一次
  1. 客戶端傳送 "#restart\n"
  2. 伺服器傳送 "#restart\tID\n"
  3. 玩家收到 ID 之後更新自己的 ID
  4. 伺服器讓玩家重生
- 傳送完整排名
  1. 客戶端傳送 "#rank\n"
  2. 伺服器傳送 "#rank\tRANK\tID\tNAME\tAGE\n" 很多次
- 離開
  1. 客戶端傳送 "#exit\n"
  2. 伺服器傳送 "#bye\n" 然後斷線
  3. 客戶端收到之後關閉遊戲
- 更新玩家名單（每當有玩家登入、重生、死亡、斷線、離開）
  1. 伺服器傳送 "#update\tID\tNAME\tRANK\n" 很多次
  2. 包含 id, name, rank 的表
  3. 用來標示名字、畫出即時排名
- 宣告死亡
  1. 伺服器傳送 "#die\n"
  2. 玩家收到之後結束遊戲，跳出提示


#### UDP
- 玩家傳送控制資料，共 20 bytes，(x, y) 是單位向量
  1. int id (4 bytes)
  2. double x (8 bytes)
  3. double y (8 bytes)
- 伺服器傳送所有玩家跟子彈的位置與狀態，序列化過的 World 物件
  - ArrayList<Player>
    - id
    - age
    - x, y
    - status { 0: spawn, 1: normal, 2: explode, 3: die }
  - ArrayList<Bullet>
    - x, y
    - status { 0: normal, 1: explode }

https://www.tutorialspoint.com/java/java_serialization.htm

### 伺服器

- UDP, TCP 的接收端在同個 PORT

#### 執行緒

1. 偵測碰撞等狀態
2. UDP 等待玩家的輸入資料，更新玩家移動速度
3. UDP 每隔一段時間就對所有連著的玩家送出所有物體的資料
4. TCP 等待玩家事件
