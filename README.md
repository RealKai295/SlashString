![Icon](https://cdn.modrinth.com/data/cached_images/0c3ed27fbe31c052e899a810bf37234ab4461b4c.png)
##
Recent **Minecraft Java** updates have closed several **string** and **tripwire** duplication tricks that farms and players used for years. If your world just lost that supply, **SlashString** is a fair replacement: one command, and **you** set how much string they get and how often, with no exploits involved.

---

### What does it do?

- Players type **`/string`** and receive a stack of string (the item used for bows, wool, tripwires, and lots of crafts).
- You choose **how many** they get and how long they must **wait** before using the command again.
- If their inventory is full, you can choose whether **extra string falls on the ground** or **is thrown away**.

There is **no permission plugin** setup: everyone who can use commands on your server can use `/string`, unless you block the command yourself in your host’s files.

---

### Installation

1. Download the plugin **.jar** from this project’s files page.
2. Put the jar in your server’s **`plugins`** folder.
3. **Restart** the server (or load the plugin the way you usually do).

You need a **Paper** (or Paper-style) Minecraft server. The plugin is built for recent **1.21** releases; if your server version does not match, ask your host or check the project’s version tags before installing.

---

### Commands

| Command | Who can use it? |
|---------|------------------|
| **`/string`** | Players in the world. They get string according to your settings. |
| **`/string reload`** | The **server console** always works. **Players** must be a **server operator** (opped) to reload. |

Reloading applies changes from your config and message files **without** restarting the whole server.

---

### Settings (`config.yml`)

After the first start, open:

**`plugins/SlashString/config.yml`**

| Setting | What it means |
|---------|----------------|
| **amount** | How many string items one use of `/string` gives (at least 1). |
| **cooldown-seconds** | How many seconds a player must wait before `/string` works again. Use **0** for no cooldown. |
| **drop-if-full** | **true**: if the inventory is full, extra string **drops at their feet**. **false**: extra string **does not drop** (it is removed). |

Edit the file, then run **`/string reload`** from the console (or in-game if you are opped).

---

### Messages (`messages.yml`)

Text the plugin sends (cooldown warnings, “you received string”, reload success, and so on) lives in:

**`plugins/SlashString/messages.yml`**

You can change the wording and colors. Colors use the usual **`&` codes** (for example **`&a`** green, **`&c`** red, **`&e`** yellow, **`&7`** gray). Placeholders like **`{amount}`** are filled in automatically. Keep those spelled exactly as in the default file.

After editing, use **`/string reload`** so players see the new text right away.

---

## License

- 🚫 You may not claim this plugin as your own.
- 🚫 You may not resell or redistribute this plugin.
- ✅ You may modify this plugin for personal use only; redistribution is not permitted.
