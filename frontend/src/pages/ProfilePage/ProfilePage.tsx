// import React, { useState } from "react";
// import "./ProfilePage.css";
// import { useAuth } from "../../context/AuthContext";
//
// export default function ProfilePage() {
//     const [open, setOpen] = useState<number | null>(null);
//
//
//     type Subtitle = {
//         title: string;
//         desc: string;
//     }
//
//     type Item = {
//         id: number;
//         title: string;
//         subtitle?: Subtitle[];
//     };
//
//
//     const items: Item[] = [
//         {
//             id: 1,
//             title: "Мой аккаунт",
//             subtitle: [
//                 { title: "Логин", desc: user.login },
//                 { title: "Телефон", desc: user.mobile },
//                 { title: "Email", desc: user.email },
//             ],
//         },
//         {
//             id: 2,
//             title: "Персональные данные",
//             subtitle: [
//                 { title: "Дата регистрации", desc: "—" },
//                 { title: "Пол", desc: "—" },
//                 { title: "Язык", desc: "Русский" },
//             ],
//         },
//         {
//             id: 3,
//             title: "Мои заказы",
//         },
//         {
//             id: 4,
//             title: "Контакты",
//             subtitle: [{ title: "Почта", desc: user.email }],
//         },
//     ];
//
//
//     return (
//         <div className="profile">
//             {items.map((item) => (
//                 <div className="profile-item" key={item.id}>
//                     <button
//                         className="profile-header"
//                         onClick={() => setOpen(open === item.id ? null : item.id)}
//                     >
//                         <span className="title">{item.title}</span>
//                         <span className={open === item.id ? "arrow open" : "arrow"}>⌄</span>
//                     </button>
//
//                     {open === item.id && (
//                         <div className="profile-body">
//                             <div className="subtitle">
//                                 {item.subtitle?.map((subt) => (
//                                     <div>
//                                         <span className="subtitle-upper-text">{subt.title}</span>
//                                         <br></br>
//                                         <span className="subtitle-lower-text">{subt.desc}</span>
//                                     </div>
//                                 ))}
//                             </div>
//                             <button className="edit-btn">
//                                 <span className="edit-text">Редактировать</span>
//                             </button>
//                         </div>
//                     )}
//                 </div>
//             ))}
//         </div>
//     );
//
// }
import { useState } from "react";
import "./ProfilePage.css";
import { useAuth } from "../../context/AuthContext";

export default function ProfilePage() {
    const { user } = useAuth();
    const [open, setOpen] = useState<number | null>(1); // сразу открыт

    if (!user) {
        return <div style={{ padding: 40 }}>Вы не авторизованы</div>;
    }

    const items = [
        {
            id: 1,
            title: "Мой аккаунт",
            subtitle: [
                { title: "Логин", desc: user.login },
                { title: "Телефон", desc: user.mobile },
                { title: "Email", desc: user.email },
            ],
        },
    ];

    return (
        <div className="profile">
            {items.map((item) => (
                <div className="profile-item" key={item.id}>
                    <button
                        className="profile-header"
                        onClick={() => setOpen(open === item.id ? null : item.id)}
                    >
                        <span className="title">{item.title}</span>
                        <span className={open === item.id ? "arrow open" : "arrow"}>
                            ⌄
                        </span>
                    </button>

                    {open === item.id && (
                        <div className="profile-body">
                            <div className="subtitle">
                                {item.subtitle.map((subt, i) => (
                                    <div className="subtitle-item" key={i}>
                                        <span className="subtitle-upper-text">
                                            {subt.title}
                                        </span>
                                        <br />
                                        <span className="subtitle-lower-text">
                                            {subt.desc}
                                        </span>
                                    </div>
                                ))}
                            </div>
                        </div>
                    )}
                </div>
            ))}

            <ChangePassword />
        </div>
    );
}
function ChangePassword() {
    const { user, setUser } = useAuth(); // получаем setUser
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [repeatPassword, setRepeatPassword] = useState("");
    const [error, setError] = useState("");

    const handleChangePassword = async () => {
        if (!oldPassword || !newPassword) {
            setError("Заполните все поля");
            return;
        }

        if (newPassword !== repeatPassword) {
            setError("Пароли не совпадают");
            return;
        }

        setError("");

        try {
            const res = await fetch(
                "http://localhost:8080/api/customers/change-password",
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    credentials: "include",
                    body: JSON.stringify({
                        oldPassword,
                        newPassword,
                    }),
                }
            );

            if (!res.ok) {
                const text = await res.text();
                throw new Error(text || "Ошибка при смене пароля");
            }

            // 1️⃣ Обновляем user в контексте, чтобы фронт видел изменения
            // Если бэк возвращает объект пользователя, можно делать так:
            // const updatedUser = await res.json();
            // setUser(updatedUser);

            // 2️⃣ Если бэк не возвращает пользователя, просто сбрасываем сессию:
            setUser(null);
            alert("Пароль успешно изменён. Пожалуйста, войдите заново.");
            window.location.href = "/login";

        } catch (e: any) {
            setError(e.message);
        }
    };

    return (
        <div className="profile-item">
            <button className="profile-header">
                <span className="title">Смена пароля</span>
            </button>

            <div className="profile-body">
                <div className="subtitle">
                    <input
                        type="password"
                        placeholder="Старый пароль"
                        value={oldPassword}
                        onChange={(e) => setOldPassword(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="Новый пароль"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="Повторите пароль"
                        value={repeatPassword}
                        onChange={(e) => setRepeatPassword(e.target.value)}
                    />
                </div>

                {error && (
                    <div style={{ color: "red", marginTop: 10 }}>{error}</div>
                )}

                <button className="edit-btn" onClick={handleChangePassword}>
                    Сменить пароль
                </button>
            </div>
        </div>
    );
}
