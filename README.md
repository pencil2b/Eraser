# Eraser Client

## TODO
- graphics/BulletRenderer.java
- graphics/PlayerRenderer.java
- 畫面順暢度

## Network

- 接口
    - 伺服器：TCP 與 UDP 同 port，伺服器運行時設定。
    - 客戶端：port 讓電腦自己選，UDP Port 在登入時會傳給伺服器。

### 玩家離開就直接斷線喔

### TCP

**以下傳送的資料空白處都用 `\t` 隔開，TCP 送的時候記得加 `\n`**

- 客戶端請求登入
    1. 客戶端：`<NAME> <UDP_PORT>`
    2. 伺服器：`id <ID>`
    3. 客戶端開始遊戲

- 客戶端請求完整排名
    1. 客戶端：`full`
    2. 伺服器：`full <PLAYER_COUNT> <ID> <NAME> <AGE> ...` 「照名次排」
    3. 客戶端顯示完整排名

- 客戶端請求重生
    1. 客戶端：`restart`
    3. 客戶端開始遊戲

- 客戶端告知伺服器可見範圍（當客戶端改變視窗大小）
	1. 客戶端：`visible <WIDTH> <HEIGHT>`

- 伺服器告知客戶端地圖大小
	1. 伺服器：`size <WIDTH> <HEIGHT>`
	2. 客戶端修改 World 的長寬

- 伺服器更新玩家名單（當有玩家登入、離開、斷線、死亡、重生）
    1. 伺服器：`list <PLAYER_COUNT> <ID> <NAME> ...`
    2. 客戶端更新玩家名單與即時排名，只傳 ID 跟 NAME，照著 Rank 排
	3. 客戶端顯示即時排名

- 伺服器宣告玩家死亡
    1. 伺服器：`die`
    2. 玩家結束遊戲，呼叫 GameOver Dialog


### UDP

＊盡量減少封包大小

- 伺服器廣播物件資料給客戶端（轉成 bytes 全部串在一起一個封包）
    - **玩家數量：short**
    - **子彈數量：short**
    - 該玩家所有可見的玩家資料（多筆）
        - **ID：short**
        - **X：float**
        - **Y：float**
        - **Age：short**
        - **Status：byte**
    - 該玩家所有可見的子彈資料（多筆）
        - **X：float**
        - **Y：float**

- 客戶端傳送控制資料給伺服器（同上，為單位向量）
    - **ID：short**
    - **X：float**
    - **Y：float**

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
byte[] buffer = udp.read();
ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
DataInputStream dis = new DataInputStream(bais);
int id = dis.readInt();
float x = dis.readFloat();
float y = dis.readFloat();
```

## 遊戲物件

### Player

- 半徑：10 + age / 2 (pixel)
- Age：每秒加一
- Status：
    - 0 = 剛重生
    - 1 = 正常
    - 2 = 死亡

### Bullet

- 半徑：5 pixel

## 控制

### 滑鼠控制資料的傳送
包含ＩＤ、Ｘ向量、Ｙ向量，以視窗中心為圓心，Ｒ為一百，若滑鼠移到超過Ｒ的地方，則傳送相對於圓心的單位向量，若滑鼠在Ｒ內距離圓心Ｄ，則傳送單位向量乘以Ｄ／Ｒ。

## 其他
- 地圖大小：3600x3600 pixel^2

### 率
- 伺服器廣播物件位置與資訊：30 hz
	- 只廣播玩家可見範圍內的東西
- 客戶端送出控制資料：30 hz
- 客戶端畫面更新率：120 hz
- 伺服器碰撞與移動更新率：120 hz
