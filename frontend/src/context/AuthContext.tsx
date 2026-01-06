// import { createContext, useContext, useEffect, useState } from "react";
// import axios from "axios";
//
// type User = {
//     id: number;
//     login: string;
//     email: string;
//     mobile: string;
// };
//
// type AuthContextType = {
//     user: User | null;
//     loading: boolean;
//     setUser: (user: User | null) => void;
// };
//
// const AuthContext = createContext<AuthContextType | null>(null);
//
// export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
//     const [user, setUser] = useState<User | null>(null);
//     const [loading, setLoading] = useState(true);
//
//     useEffect(() => {
//         axios
//             .get("http://localhost:8080/api/auth/me", {
//                 withCredentials: true,
//             })
//             .then((res) => setUser(res.data))
//             .catch(() => setUser(null))
//             .finally(() => setLoading(false));
//     }, []);
//
//     return (
//         <AuthContext.Provider value={{ user, loading, setUser }}>
//             {children}
//         </AuthContext.Provider>
//     );
// };
//
// export const useAuth = () => {
//     const ctx = useContext(AuthContext);
//     if (!ctx) throw new Error("useAuth must be used within AuthProvider");
//     return ctx;
// };


import { createContext, useContext, useEffect, useState } from "react";
import axios from "axios";

export type User = {
    id: number;
    login: string;
    email: string;
    mobile: string;
};

type AuthContextType = {
    user: User | null;
    loading: boolean;
    setUser: (user: User | null) => void;
    logout: () => void;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

    // Проверка авторизации при загрузке страницы
    const fetchUser = async () => {
        try {
            const res = await axios.get("http://localhost:8080/api/customers/me", {
                withCredentials: true, // важно для cookie
            });
            setUser(res.data);
        } catch {
            setUser(null);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUser();
    }, []);

    const logout = async () => {
        try {
            await axios.post("http://localhost:8080/api/auth/logout", {}, {
                withCredentials: true
            });
        } catch (err) {
            console.error(err);
        } finally {
            setUser(null);
        }
    };

    return (
        <AuthContext.Provider value={{ user, loading, setUser, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const ctx = useContext(AuthContext);
    if (!ctx) throw new Error("useAuth must be used within AuthProvider");
    return ctx;
};
