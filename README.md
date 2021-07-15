# maimaiRollBot
A telegram bot to help you choose song randomly while playing maimaiDX2021.
## To Do
- [ ]  Roll
    - [x] Roll from all song
    - [x] Roll by level
    - [ ] Roll by base
    - [ ] Roll by categories
- [ ] Inline mode
    - [ ]  Roll
        - [x] Roll from all song
        - [x] Roll by level
        - [ ] Roll by base
        - [ ] Roll by categories
## How to use
Now available on telegram [@maimaiRollBot](https://t.me/maimaiRollBot)
Just send `\roll` to roll a song.
This command can also go with a param, `\roll 15` ,for example.
#### Inline mode
Just type `@maimaiRollBot ` on any chat input and choose a song to send.
## Deploy
You can deploy this to your own server (though I don't recomend this).
Java environment is required.
To deploy,follow the steps.
#### Step 1: get the executable jar
You can find one in [Workflow Artifacts](https://github.com/realZnS/maimaiRollBot/actions)
Use curl or wget to download it.
Additionally,you can compile from source code
```bash
git clone https://github.com/realZnS/maimaiRollBot.git
cd maimaiRollBot
mvn package
```
Then you can find a jar file named `maimaiRollBot-*-with-dependencies.jar` in the folder `target`
#### Step 2: get&set your bot token
Go talk to [@BotFather](https://t.me/BotFather) and you should get a token like `xxxxxxxxxx:ABC......ABC`
run `java -jar maimaiRollBot-*-with-dependencies.jar`,and for the first run,it would generate a json,just put your token in the json.
#### Step 3: get song list
```bash
wget https://raw.githubusercontent.com/realZnS/maimaiRollBot/master/maimaidxCN.json
```
#### Step 4: run&enjoy
```bash
java -jar maimaiRollBot-*-with-dependencies.jar
```
To start on boot,you can use `systemctl`.
