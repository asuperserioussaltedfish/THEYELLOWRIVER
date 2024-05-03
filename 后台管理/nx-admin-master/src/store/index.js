import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import common from './modules/common'
import getters from './getters'
import fullScreen from './modules/fullScreen'
import permission from './modules/permission'
import tagsView from './modules/tagsView'
import errorLog from './modules/errorLog'

import user from './modules/user'
import data from './modules/data'
Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    user,
    data,
    common,
    fullScreen,
    permission,
    tagsView,
    errorLog

  },
  getters
})

export default store
