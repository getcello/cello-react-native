import {
  withInfoPlist,
  createRunOncePlugin,
  withStringsXml,
  AndroidConfig,
} from '@expo/config-plugins';
import type { ConfigPlugin } from '@expo/config-plugins';

const pkg = require('@getcello/cello-react-native/package.json');

type CelloPluginProps = {
  env?: string;
  linkHosts?: string[];
};

const androidString: ConfigPlugin<CelloPluginProps> = (
  _config,
  { env, linkHosts }
) => {
  return withStringsXml(_config, (config) => {
    if (env) {
      config.modResults = AndroidConfig.Strings.setStringItem(
        [{ $: { name: 'cello_env' }, _: env }],
        config.modResults
      );
    }
    if (linkHosts?.length) {
      const resources = config.modResults.resources as Record<string, any>;
      resources['string-array'] = [
        ...(resources['string-array'] ?? []).filter(
          (array: any) => array.$?.name !== 'cello_link_hosts'
        ),
        { $: { name: 'cello_link_hosts' }, item: linkHosts },
      ];
    }
    return config;
  });
};

const withCelloAndroid: ConfigPlugin<CelloPluginProps> = (config, props) => {
  config = androidString(config, props);
  return config;
};

const infoPlist: ConfigPlugin<CelloPluginProps> = (
  _config,
  { env, linkHosts }
) => {
  return withInfoPlist(_config, (config) => {
    if (env) {
      config.modResults.CELLO_ENV = env;
    }
    if (linkHosts?.length) {
      config.modResults.CelloLinkHosts = linkHosts;
    }
    return config;
  });
};

const withCelloIOS: ConfigPlugin<CelloPluginProps> = (config, props) => {
  config = infoPlist(config, props);
  return config;
};

const withCelloReactNative: ConfigPlugin<CelloPluginProps> = (
  config,
  props = {}
) => {
  config = withCelloAndroid(config, props);
  config = withCelloIOS(config, props);
  return config;
};

export default createRunOncePlugin(withCelloReactNative, pkg.name, pkg.version);
