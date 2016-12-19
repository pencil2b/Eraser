# Eraser Client

本專案為一個連線遊戲，讓玩家輸入名稱與伺服器的 ip 位址連進來遊玩，玩家會是一顆球，四周有到處亂飛的小型子彈，玩家得透過滑鼠操控來躲避它們，一旦子彈碰到玩家，玩家就輸了，看誰能存活最久。

## 流程

*START* => (1)

(1) *StartDialog* 登入視窗
{ 使用者輸入 Dorian@127.0.0.1:20000 按下 OK }
- 連線成功 => (2)
- 輸入錯誤、離開或連線失敗 => *EXIT*


(2) *GameWindow* 遊戲主畫面
{ 使用者透過滑鼠操作球球 }
- 被子彈射死 => (3)
- 按叉叉 => *EXIT*


(3) *GameOverDialog* 遊戲結束視窗 
- 選 Restart => (2)
- 選 Full Rank => (4)
- 選 Exit => *EXIT*

(4) *RankListDialog* 完整排名視窗
- 離開 => (3)

*EXIT* => 結束遊戲


## 規格

```
A. 控制資料的傳送
B. 使用者介面
    StartDialog
    GameWindow
    GameOverDialog
    RankListDialog
C. 畫面
D. 網路
    概述
    TCP 資料格式
    UDP 資料格式
E. 遊戲物件
    子彈
    RANK
    Player
    User
F. 其他
```

### A. 控制資料的傳送

- 傳送資料：遊戲開始之後，客戶端會每秒傳送 30 次控制資料給伺服器，包含 ID、X向量、Y向量
- 計算方式：以 Canvas 中心為圓心，R 為 100 作圓，若滑鼠在圓外，則傳送始於圓心的單位向量，若滑鼠在圓內距離圓心 d，則傳送單位向量乘以 d/R。

### B. 使用者介面

#### StartDialog

- JOptionPane 的 InputDialog
- 包含登入提示與輸入框
- 輸入格式為 `<NAME>@<IP>:<PORT>`

#### GameWindow

- 大小：800*600 px
- 裡面只有一個 Canvas

#### GameOverDialog

- JOptionPane 的 OptionDialog
- 包含遊玩結果與三個按鈕
- 結果（提示文字）：
    - `Result\n`
    - `Name: <NAME>\n` 玩家名稱
    - `Age: <AGE>\n` 玩家存活秒數
    - `Rank: <RANK>\n` 玩家排名
- 按鈕：
    - `[Restart]` 復活，向伺服器傳送 restart 訊息，關閉 Dialog
    - `[Full Rank]` 傳送 full 訊息給伺服器，收到排名後跳出 RankListDialog
    - `[Exit]` 直接斷線離開遊戲

#### RankListDialog

- JOptionPane 的 MessageDialog
- 包含可捲動的 JScrollPane (600x400) px
- ScrollPane 裡面有不可編輯的 JTextArea
- 收到伺服端傳來的完整排名則依 Rank Age ID Name 一行行寫入 TextArea
- 關閉此 Dialog 會回到 GameOverDialog

### C. 畫面

- 背景為 100 x 100 的方格，超出地圖範圍不畫
- 每秒將 World 實體畫出來 120 次
    - UDP 每秒傳送 30 次資料來更新 World 實體 （如果會延遲則再調整) 


### D. 網路

#### 概述

- Port：伺服端 TCP 接收端與 UDP 接收端同 port，玩家 UDP 接收端的 port 會在 TCP 連上時傳給伺服器，這樣伺服器才知道要廣播到各戶端的哪個 port。
- 玩家若要重生會送出 restart 訊息，若要離開則直接斷線，伺服端必須處理好斷線問題。
- 重要訊息透過 TCP 傳送，即時動態透過 UDP 傳送。

#### TCP 資料格式

以下列出客戶端與伺服器溝通時會用到的訊息格式。

為了看起來更明確，以下傳送的資料真的在傳送時空白處都用 `\t` 代替，TCP 送的時候也記得加 `\n`，角括號處是傳送端要填的地方。
例如登入時名字為 YPC、UDP port 為 23345，則：
`<NAME> <UDP_PORT>` 是傳 `YPC\t23345\n`；`id <ID>` 是傳 `"id\t12\n"`

- 客戶端請求登入
    1. 客戶端：`<NAME> <UDP_PORT>`
    2. 伺服器：`id <ID>`
    3. 客戶端可開始遊戲

- 客戶端請求完整排名
    1. 客戶端：`full`
    2. 伺服器：`full <PLAYER_COUNT> <ID> <NAME> <AGE> <ID> <NAME> <AGE> ...` 「照名次排」
    3. 然後客戶端顯示完整排名

- 客戶端請求重生
    1. 客戶端：`restart`

- 伺服器告知客戶端地圖大小
	1. 伺服器：`size <WIDTH> <HEIGHT>`
	2. 客戶端修改 World 的長寬，這樣才知道背景要畫到哪裡

- 伺服器更新玩家名單（當有玩家登入、斷線、死亡、重生）
    1. 伺服器：`list <PLAYER_COUNT> <ID> <NAME> <ID> <NAME> ...` 照 RANK 排
    2. 客戶端更新玩家名單與即時排名，只傳 ID 跟 NAME，照著 Rank 排
	3. 客戶端顯示即時排名

- 伺服器宣告玩家死亡
    1. 伺服器：`die`
    2. 玩家結束遊戲，呼叫 GameOverDialog

#### UDP 資料格式

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
        - **VX : float**
        - **VY : float** 

- 客戶端傳送控制資料給伺服器（同上，為單位向量）
    - **ID：short**
    - **X：float**
    - **Y：float**

處理 byte[] 轉換

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

### E. 遊戲物件

- Player 玩家
    - ID
        - 由 Server 照連線次數遞增，再傳給 Client
    - Name
        - 玩家自行輸入
    - X, Y
        - 起始位置 x = random.nextFloat()*起始範圍的長 + 與邊界距離
        - 起始位置 y = random.nextFloat()*起始範圍的寬 + 與邊界距離
    - Age
        - 玩家大小計算方式：半徑為 10 + age / 2 pixel
        - Age 每秒加一
    - Status
        - 0: 剛重生（不會遭受攻擊）
        - 1: 普通
        - 2: 死亡（無法移動）
- Bullet 子彈
    - X, Y
        - 半徑為 5 pixel

### F. 其他

#### 率
- 伺服器廣播物件位置與資訊：30 hz
	- 只廣播玩家可見範圍內的東西
- 客戶端送出控制資料：30 hz
- 客戶端畫面更新率：120 hz
- 伺服器碰撞與移動更新率：120 hz

#### 地圖與子彈的產生

- 地圖大小：3600 x 3600 px
- 子彈： 
    - 速度： (SPEED_MAX-SPEED_LEAST)*rand.nextFloat()+SPEED_LEAST;
    - 方向： 由生出的位置，朝內部發射
    - 位置： 固定x或y，另一個取亂數 （＝邊界）

## 設計

- World (static)
    - Player findPlayer(int id)
    - void setSize(int width, int height)
    - void update(ArrayList players, ArrayList bullets)
    - int getWidth(), getHeight()
    - ArrayList getPlayers(), getBullets()
- Control (static)
    - MouseListener getInstance() : 回傳滑鼠 Listener
    - ControlData getData() : 回傳目前方向向量
- Network (static)
    - void updateWorld() : 等待UDP訊息
    - void dispatchEvent() : 等待伺服器TCP事件
    - ArrayList receiveFullList() : 接收伺服器完整排名
    - void sendControl() : UDP 送出方向向量
    - int sendLoginAndGetId() : 送出 UDP 接收阜取得 ID
    - void sendRestart() : 要求伺服器重生自己
    - void sendFullListRequest() : 要求完整排名
- Events (static)
    - void setWorldSize(int width, int height) : 設定 World 大小
    - void updateNameList(HashMap nameList) : 更新即時排名（玩家名單）
    - void updateRankList(ArrayList rankList) : 更新完整排名
    - void updateWorld(ArrayList newPlayers, ArrayList newBullets) : 更新 World
    - void die() : 死亡
    - ArrayList getRankList()
    - HashMap getNameList()
- Graphics (static)
    - void init()
    - Point getCenter() : 取得 Canvas 中心
    - void render() 
- Game (static)
    - int getId() 
    - String getServer()
