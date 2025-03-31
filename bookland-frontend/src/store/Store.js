import { configureStore } from '@reduxjs/toolkit'
import { combineReducers } from 'redux'
import {
    persistStore,
    persistReducer,
    FLUSH,
    REHYDRATE,
    PAUSE,
    PERSIST,
    PURGE,
    REGISTER,
} from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import { thunk } from 'redux-thunk'
import TokenReducer from "./reducers/TokenSlice.js"
import UserReducer from "./reducers/UserSlice.js"

const reducers = combineReducers({
    Token: TokenReducer,
    user: UserReducer
})

const persistConfig = {
    key: 'redux-store',
    storage,
}

const persistedReducer = persistReducer(persistConfig, reducers)

const Store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: {
                ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
            },
        }).concat(thunk),
})

export const persistor = persistStore(Store)
export default Store;

