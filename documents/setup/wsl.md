## WSL セットアップ (Windows の場合)

このプロジェクトは WSL 環境で開発することを推奨しています。Windows で文字化けを避けるために、WSL を使用してください。

### WSL のインストール

1. **管理者権限でターミナルを開く**

   - スタートボタンを右クリックし、「ターミナル（管理者）」または「Windows PowerShell（管理者）」を選択

2. **WSL のインストール**

   ```powershell
   wsl --install
   ```

3. **再起動**

   - インストール完了後、PC を再起動してください

4. **WSL の起動**
   - 再起動後、Ubuntu が自動的に起動します
   - 初回起動時は、ユーザー名とパスワードの設定を求められます

### WSL での開発環境セットアップ

1. **JDK 21 のインストール**

   ```bash
   sudo apt update
   sudo apt install openjdk-21-jdk
   ```

2. **Docker の設定**

   - Docker Desktop をインストールしている場合、WSL 統合を有効にしてください
   - Docker Desktop の設定 > Resources > WSL Integration で、使用する WSL ディストリビューションを有効化

3. **プロジェクトのクローン/移動**

   ```bash
   # Windows のファイルシステムからアクセスする場合 ※重たくなりがちです。WSL側へのクローンを推奨します。
   cd /mnt/d/git/dev-fjk/web-app-sample

   # または、WSL のファイルシステムにクローンする場合（推奨）
   git clone <repository-url>
   cd web-app-sample
   ```

### VS Code での WSL 使用

1. VS Code で「Remote - WSL」拡張機能をインストール
2. WSL ターミナルから `code .` を実行するか、VS Code で「Remote WSL: Open Folder in WSL」を選択
