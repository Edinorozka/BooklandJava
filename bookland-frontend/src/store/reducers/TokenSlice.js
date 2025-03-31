import { createSlice} from "@reduxjs/toolkit"

const initialState = {
    token: '',
    refresh: ''
}

const TokenSlice = createSlice({
    name: "Token",
    initialState,
    reducers: {
        getToken: (state, action) => {
            state.token = action.payload.token
            state.refresh = action.payload.refresh
        },
        refreshToken: (state, action) => {
            state.token = action.payload.token
        },
        deleteToken: (state) => {
            state.token = ''
            state.refresh = ''
        }
    }
})

export const { getToken, refreshToken, deleteToken } = TokenSlice.actions
export default TokenSlice.reducer
