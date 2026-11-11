const { createHash } = require('node:crypto');
const fs = require('node:fs');
const path = require('node:path');

const root = path.resolve(__dirname, '..');
const { version, checksum } = require(path.join(root, 'package.json')).celloSdk
  .ios;
const vendorDir = path.join(root, 'vendor', 'ios');
const zipPath = path.join(vendorDir, 'CelloSDK.xcframework.zip');
const url = `https://github.com/getcello/cello-ios-sp/releases/download/${version}/CelloSDK.xcframework.zip`;

function sha256(data) {
  return createHash('sha256').update(data).digest('hex');
}

async function main() {
  if (fs.existsSync(zipPath) && sha256(fs.readFileSync(zipPath)) === checksum) {
    return;
  }
  const response = await fetch(url);
  if (!response.ok) {
    throw new Error(`Downloading ${url} failed with HTTP ${response.status}`);
  }
  const zip = Buffer.from(await response.arrayBuffer());
  const actual = sha256(zip);
  if (actual !== checksum) {
    throw new Error(
      `CelloSDK ${version} checksum mismatch: expected ${checksum}, got ${actual}`
    );
  }
  fs.rmSync(vendorDir, { recursive: true, force: true });
  fs.mkdirSync(vendorDir, { recursive: true });
  fs.writeFileSync(zipPath, zip);
  console.log(`CelloSDK ${version} for iOS is in vendor/ios`);
}

main().catch((error) => {
  console.error(error.message);
  process.exit(1);
});
