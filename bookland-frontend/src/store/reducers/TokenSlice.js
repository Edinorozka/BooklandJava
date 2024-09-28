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
            state.refresh = action.payload.refreshToken
        },
        deleteToken: (state) => {
            state.token = ''
            state.refresh = ''
        }
    }
})

export const { getToken, deleteToken } = TokenSlice.actions
export default TokenSlice.reducer
