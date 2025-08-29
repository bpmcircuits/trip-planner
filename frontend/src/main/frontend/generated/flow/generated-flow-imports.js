import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/icon/src/vaadin-icon.js';
import '@vaadin/password-field/src/vaadin-password-field.js';
import '@vaadin/button/src/vaadin-button.js';
import 'Frontend/generated/jar-resources/disableOnClickFunctions.js';
import '@vaadin/checkbox/src/vaadin-checkbox.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/app-layout/src/vaadin-app-layout.js';
import '@vaadin/app-layout/src/vaadin-drawer-toggle.js';
import '@vaadin/side-nav/src/vaadin-side-nav.js';
import '@vaadin/side-nav/src/vaadin-side-nav-item.js';
import '@vaadin/scroller/src/vaadin-scroller.js';
import '@vaadin/combo-box/src/vaadin-combo-box.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import 'Frontend/generated/jar-resources/comboBoxConnector.js';
import '@vaadin/multi-select-combo-box/src/vaadin-multi-select-combo-box.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '14f1ad9f39f3d8e87b724bd299f27c40cf0371432fb242214f9732d7e1d181e7') {
    pending.push(import('./chunks/chunk-937ce73c52e82b440fe24cd41f11129aa880a9d7b30908e240edd59e9fb39d8d.js'));
  }
  if (key === 'a9c1bc4636d1ce379a963006003edcfbd48d47307a7359829d20dee1c854b525') {
    pending.push(import('./chunks/chunk-ee95395a47a2fefeb0c9ea924b2d3128324e36fc08fef992a79741bd4de43b7b.js'));
  }
  if (key === '0e5bcb196682404454b94c34e4a4aa5f59298b90fc4c5c116d5d40866b2c736a') {
    pending.push(import('./chunks/chunk-78e032d8ce03454c46dcbc8802aa6f3cb11708cde50c6e3893d70d9360f0963d.js'));
  }
  if (key === '033314e9efc65ab20008840c1688fcbea5d1aa6a985ddcd6282f6c41c1733ef0') {
    pending.push(import('./chunks/chunk-78e032d8ce03454c46dcbc8802aa6f3cb11708cde50c6e3893d70d9360f0963d.js'));
  }
  if (key === '9bdf898ff6f3f21e0a56bd9a27e55d4d3072487905f45c3a4154492fc9e5f972') {
    pending.push(import('./chunks/chunk-9bf587aaf0d7a0bc184771439ae72daccb655fbd4b1c6771a5fb96fd3458ee4c.js'));
  }
  if (key === '6c02c748c559337d6f37130eb7ad071268b502eaf3631648f46683f6c520e550') {
    pending.push(import('./chunks/chunk-20c1a928ca65e6a0716bdea3e34cbcab92a3e8e69851107c2e7f1159043c8ebd.js'));
  }
  if (key === '6db8f1220d6e2598cc58f6a25be67a2fa685a1559d4c5609b748a00dcbbfe43c') {
    pending.push(import('./chunks/chunk-1683324d885d6e52f7e1e6bb520923934d5e5b562e4a5ec37905bd42ee6ae6e9.js'));
  }
  if (key === '5ab90a11e0c56798c6825cf846306f18bc20bb76420640cf90e3d7cb7a195de7') {
    pending.push(import('./chunks/chunk-1683324d885d6e52f7e1e6bb520923934d5e5b562e4a5ec37905bd42ee6ae6e9.js'));
  }
  if (key === '57d9708c0ca780a42809789eae3ccced5365aa91ae16de45504c68b3cc762575') {
    pending.push(import('./chunks/chunk-15f069f18c587193977e738bb6680b72763a4ef5f369784d9ef1f8419af9868b.js'));
  }
  if (key === 'f59e7d232c9f77f8fb0cfb1bfc6343e42a530331dd4ddba87c85bc9f9b2bf62d') {
    pending.push(import('./chunks/chunk-0288bab915d3ec73ac744968d51807312993e0c2e7149977dc29b4dce5f33c50.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}