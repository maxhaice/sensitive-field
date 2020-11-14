export interface User{
    name: string,
    login: string,
    password: string,
    personalOptions: {
        left_navbar: boolean,
        sound_all: boolean,
        back: string,
        s_back: string,
        text: string,
        s_text: string
    }
}