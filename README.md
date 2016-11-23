# 規格書

## Network

- 接口
    - 伺服器：TCP 與 UDP 同 port，伺服器運行時設定。
    - 客戶端：port 讓電腦自己選，UDP Port 在登入時會傳給伺服器。

### TCP

以下傳送的資料空白處都用 `\t` 隔開，TCP 送的時候記得加 `\n`

- 客戶端請求登入
    1. 客戶端：`<NAME> <UPD_PORT>`
    2. 伺服器：`id <ID>`
    3. 客戶端開始遊戲

- 客戶端請求完整排名
    1. 客戶端：`rank`
    2. 伺服器：`rank BEGIN`
    3. 伺服器：`rank <ID> <NAME> <AGE> <RANK>` n 次
    4. 伺服器：`rank END`
    5. 客戶端顯示完整排名

- 客戶端請求重生
    1. 客戶端：`restart`
    2. 伺服器：`id <ID>`
    3. 客戶端開始遊戲

- 客戶端請求離開
    1. 客戶端：`exit`
    2. 伺服器斷線，客戶端關閉遊戲

- 伺服器更新玩家名單（當有玩家登入、離開、斷線、死亡、重生）
    1. 伺服器：`list BEGIN`
    2. 伺服器：`list <ID> <NAME> <RANK>` n 次
    3. 伺服器：`list END`
    4. 客戶端更新玩家名單與即時排名

- 伺服器宣告玩家死亡
    1. 伺服器：`die`
    2. 玩家結束遊戲，呼叫 EndBar


### UDP

＊盡量減少封包大小

- 伺服器廣播物件資料給客戶端（轉成 bytes 全部串在一起一個封包）
    1. 玩家數量：short
    2. 子彈數量：short
    3. 所有玩家資料
        1. ID：short
        2. X：float
        3. Y：float
        4. Age：short
        5. Status：byte
    4. 所有子彈資料
        1. X：float
        2. Y：float
        3. Status：byte

- 客戶端傳送控制資料給伺服器（同上，為單位向量）
    1. ID：short
    2. X：float
    3. Y：float

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
    - 0 = 死亡
    - 1 = 剛重生
    - 2 = 普通
    - 3 = 爆掉

### Bullet

- 半徑：5 pixel
- Status：
    - 0 = 普通
    - 1 = 爆掉

## 控制

### 滑鼠控制資料的傳送
包含ＩＤ、Ｘ向量、Ｙ向量，以視窗中心為圓心，Ｒ為一百，若滑鼠移到超過Ｒ的地方，則傳送相對於圓心的單位向量，若滑鼠在Ｒ內距離圓心Ｄ，則傳送單位向量乘以Ｄ／Ｒ。

### 鍵盤
- ESC 解除滑鼠控制

## 其他
- 地圖大小：4000x4000 $pixel^2$

### 率
- 伺服器廣播物件資料：30 hz
- 客戶端送出控制資料：30 hz
- 客戶端畫面更新率：120 hz
- 伺服器碰撞與移動更新率：120 hz
