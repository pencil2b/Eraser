# Eraser Client

### TODO
1. network/Receiver
2. graphics/\*
3. TCP event handling
4. eraser/Game

### 連線概述

#### TCP
- 登入
  1. 建立TCP連線
  2. 玩家傳送 "NAME\tPORT\n" 給伺服器，PORT 是玩家的 UDP 接收端
  3. 伺服器回應玩家一個 "#id\tID\n" 字串，收到之後客戶端開始遊戲
- 再玩一次
  1. 客戶端傳送 "#restart\n"
  2. 伺服器傳送 "#id\tID\n"
  3. 玩家收到 ID 之後更新自己的 ID
  4. 伺服器讓玩家重生
- 傳送完整排名
  1. 客戶端傳送 "#rank\n"
	2. 伺服器傳送 "#rank\tBEGIN\n"
  3. 伺服器傳送 "#rank\tRANK\tID\tNAME\tAGE\n"
	4. 伺服器傳送 "#rank\tEND\n"
- 離開
  1. 客戶端傳送 "#exit\n"
  2. 伺服器斷線
  3. 客戶端關閉遊戲
- 更新玩家名單（每當有玩家登入、重生、死亡、斷線、離開）
	1. 伺服器傳送 "#list\tBEGIN\n"
  2. 伺服器傳送 "#list\tID\tNAME\tRANK\n" 很多次 ID NAME RANK 為玩家資料
	3. 伺服器傳送 "#list\tEND\n"
  3. 包含 id, name, rank 的表
  4. 用來標示名字、畫出即時排名
- 宣告死亡
  1. 伺服器傳送 "#die\n"
  2. 玩家收到之後結束遊戲，跳出提示


#### UDP
- 玩家傳送控制資料，共 20 bytes，(x, y) 是單位向量
  1. int id (4 bytes)
  2. float x (4 bytes)
  3. float y (4 bytes)
- 伺服器傳送所有玩家跟子彈的位置與狀態，整個封包如下
 - Count { player_count: int(4), bullet_count(4) } 表示有幾個 player bullet
 - Player { id: int(4), x: float(4), y: float(4), age: int(4), status: int(4) }
 - Bullet { x: float(4), y: float(4), status: int(4) }

#### 處理 byte[] 轉換

``` java
ByteArrayOutputStream baos = new ByteArrayOutputStream();
DataOutputStream dos = new DataOutputStream(baos);
dos.writeInt(id);
dos.writeDouble(data.x);
dos.writeDouble(data.y);
byte[] buffer = baos.toByteArray();
```


### 伺服器

- UDP, TCP 的接收端在同個 PORT

#### 執行緒

1. 偵測碰撞等狀態
2. UDP 等待玩家的輸入資料，更新玩家移動速度
3. UDP 每隔一段時間就對所有連著的玩家送出所有物體的資料
4. TCP 等待玩家事件
