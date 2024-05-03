import { getData, getUser } from '@/api/data'
// import { getToken, setToken, removeToken } from '@/utils/auth'
// import {
//   setStore,
//   getStore,
//   removeStore
// } from '@/utils/store'
const data = {
  state: {
  newUser:'',
	newArticle:'',
	newSuggestion:'',
	allUser:'',
	allArticle:'',
	allSuggestion:'',
  user:[]
  },

  mutations: {
    setData(state, data) {
        state.newUser = data.newUser;
        state.newArticle = data.newArticle;
        state.newSuggestion = data.newSuggestion;
        state.allUser = data.allUser;
        state.allArticle = data.allArticle;
        state.allSuggestion = data.allSuggestion;
      },
    setUser(state, data){
      state.user=data
    }
  },

  actions: {
    // 获取实时信息
    GetData({ commit }) {
      return new Promise((resolve, reject) => {
        getData().then(response => {
          const data = response
          commit('setData', data);
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取用户信息
    GetUser({ commit }) {
      return new Promise((resolve, reject) => {
        getUser().then(response => {
          const data = response
          commit('setUser', data);
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    
  }
}

export default data
