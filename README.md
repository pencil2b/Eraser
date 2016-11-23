# Eraser Client

### 一些規格
- player.age 每秒加一
- 地圖大小 3600 x 3600
- Player 半徑就是 (age) pixels
- Bullet 半徑 5
- 滑鼠控制：超過 R 就送出單位向量，在 R 內就送出 d/R 長的向量，每秒送出 10 次

### TODO

1. graphics/\*

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
	4. 包含 id, name, rank 的表
	5. 用來標示名字、畫出即時排名

- 宣告死亡
	1. 伺服器傳送 "#die\n"
	2. 玩家收到之後結束遊戲，跳出提示


#### UDP

- 玩家傳送控制資料，共 20 bytes，(x, y) 是單位向量
	1. int id (4 bytes)
	2. float x (4 bytes)
	3. float y (4 bytes)

- 伺服器傳送所有玩家跟子彈的位置與狀態，整個封包如下
	- Count { playerCount: int(4), bulletCount(4) } 表示有幾個 player bullet
	- Player { id: int(4), x: float(4), y: float(4), age: int(4), status: int(4) }
	- Bullet { x: float(4), y: float(4), status: int(4) }

#### 處理 byte[] 轉換

``` java
ByteArrayOutputStream baos = new ByteArrayOutputStream();
DataOutputStream dos = new DataOutputStream(baos);
dos.writeInt(id);
dos.writeFloat(x);
dos.writeFloat(y);
byte[] buffer = baos.toByteArray();
```

``` java
byte[] buffer = ucp.read();
ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
DataInputStream dis = new DataInputStream(bais);
int id = dis.readInt();
float x = dis.readFloat();
float y = dis.readFloat();
```


### 伺服器

- UDP, TCP 的接收端在同個 PORT

#### 執行緒

1. 偵測碰撞等狀態對玩家發出 TCP 的事件資料
2. UDP 等待玩家的輸入資料，更新玩家移動速度
3. UDP 每隔一段時間就對所有連著的玩家送出所有物體的資料
4. TCP 等待玩家事件
