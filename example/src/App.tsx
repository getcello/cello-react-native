import Cello, { CelloEvents } from '@getcello/cello-react-native';
import React, { useEffect } from 'react';
import type { PropsWithChildren } from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
} from 'react-native';
import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

type SectionProps = PropsWithChildren<{
  title: string;
}>;

function Section({ children, title }: SectionProps): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  return (
    <View style={styles.sectionContainer}>
      <Text
        style={[
          styles.sectionTitle,
          {
            color: isDarkMode ? Colors.white : Colors.black,
          },
        ]}
      >
        {title}
      </Text>
      <Text
        style={[
          styles.sectionDescription,
          {
            color: isDarkMode ? Colors.light : Colors.dark,
          },
        ]}
      >
        {children}
      </Text>
    </View>
  );
}

function App(): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  useEffect(() => {
    intializeCello();
  }, []);

  const intializeCello = async () => {
    try {
      const config = await Cello.initialize(
        'cello.so',
        'eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJwcm9kdWN0SWQiOiJjZWxsby5zbyIsInByb2R1Y3RVc2VySWQiOiI4MGJkZWNmZC0yZjA0LTQyNmItOGI3NC1jYjhmODIwYWNmMzUiLCJpYXQiOjE3MzYzMTYzNTkuNjc2MDkzfQ.b-TL7GoScFycuCTEMVYD8mXKppkWyhW3Hm-H6EzuMQfBo0bm2PCGh5BsEN4xOFi60z1viOCX3iLW91uZowpybQ'
      );
      console.log(config);
      Cello.showFab();
    } catch (e) {
      console.log(e);
    }

    Cello.addListener(CelloEvents.tokenAboutToExpire, () => {
      console.log('about to expire');
    });

    Cello.addListener(CelloEvents.tokenHasExpired, () => {
      console.log('token expired');
    });
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}
      >
        <Header />
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
          }}
        >
          <Section title="Step One">
            Edit <Text style={styles.highlight}>App.tsx</Text> to change this
            screen and then come back to see your edits.
          </Section>
          <Section title="See Your Changes">
            <ReloadInstructions />
          </Section>
          <Section title="Debug">
            <DebugInstructions />
          </Section>
          <Section title="Learn More">
            Read the docs to discover what to do next:
          </Section>
          <LearnMoreLinks />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;
