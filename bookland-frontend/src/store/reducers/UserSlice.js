import { createSlice } from "@reduxjs/toolkit"

const initialState = {
    userLogged: false,
    login: null,
    name: '',
    role: '',
    icon: ''
}

const UserSlice = createSlice({
    name: "User",
    initialState,
    reducers: {
        getUser: (state, action) => {
            state.login = action.payload.login
            state.name = action.payload.name
            state.role = action.payload.role
            state.icon = action.payload.icon
            state.userLogged = true
        },
        deleteUser: (state) => {
            state.login = ''
            state.name = ''
            state.role = ''
            state.icon = ''
            state.userLogged = false
        }
    }
})

export const { getUser, deleteUser } = UserSlice.actions
export default UserSlice.reducer
