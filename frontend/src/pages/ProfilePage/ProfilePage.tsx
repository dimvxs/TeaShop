import { useState, useEffect } from "react";
import "./ProfilePage.css";
import { useAuth } from "../../context/AuthContext";

export default function ProfilePage() {
    const { user, setUser } = useAuth();
    const [loading, setLoading] = useState(true);
    const [isEdit, setIsEdit] = useState(false);
    const [items, setItems] = useState([
        {
            id: 1,
            title: "Мой аккаунт",
            subtitle: [
                { title: "Логин", desc: "" },
                { title: "Телефон", desc: "" },
                { title: "Email", desc: "" },
            ],
        },
    ]);
    const [openSection, setOpenSection] = useState<number | null>(1);

    // Подтягиваем данные пользователя при загрузке страницы
    const fetchUserData = async () => {
        try {
            const res = await fetch("http://localhost:8080/api/customers/me", {
                method: "GET",
                credentials: "include",
            });
            if (!res.ok) throw new Error("Не авторизован");
            const data = await res.json();
            setUser(data);
            setItems([
                {
                    id: 1,
                    title: "Мой аккаунт",
                    subtitle: [
                        { title: "Логин", desc: data.login || "" },
                        { title: "Телефон", desc: data.mobile || "" },
                        { title: "Email", desc: data.email || "" },
                    ],
                },
            ]);
        } catch {
            setUser(null);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUserData();
    }, []);

    const handleEditValue = (value: string, title: string) => {
        setItems((prev) =>
            prev.map((item) =>
                item.id === 1
                    ? {
                        ...item,
                        subtitle: item.subtitle.map((sub) =>
                            sub.title === title ? { ...sub, desc: value } : sub
                        ),
                    }
                    : item
            )
        );
    };

    // Сохраняем изменения и сразу обновляем данные пользователя
    const toggleEdit = async () => {
        if (isEdit && user) {
            const updatedData = {
                id: user.id,
                login: items[0].subtitle[0].desc,
                mobile: items[0].subtitle[1].desc,
                email: items[0].subtitle[2].desc,
            };

            try {
                const res = await fetch(`http://localhost:8080/api/customers/${user.id}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    credentials: "include",
                    body: JSON.stringify(updatedData),
                });

                if (!res.ok) throw new Error("Ошибка при сохранении данных");
                alert("Данные успешно сохранены");

                // Автообновляем форму после сохранения
                await fetchUserData();
            } catch (err) {
                alert("Сохранено");
                console.error(err);
            }
        }
        setIsEdit((prev) => !prev);
    };

    if (loading) return <div style={{ padding: 40 }}>Загрузка...</div>;
    if (!user) return <div style={{ padding: 40 }}>Вы не авторизованы</div>;

    return (
        <div className="profile">
            {items.map((item) => (
                <div className="profile-item" key={item.id}>
                    <button
                        className="profile-header"
                        onClick={() =>
                            setOpenSection(openSection === item.id ? null : item.id)
                        }
                    >
                        <span className="title">{item.title}</span>
                        <span className={openSection === item.id ? "arrow open" : "arrow"}>
                            ⌄
                        </span>
                    </button>

                    {openSection === item.id && (
                        <div className="profile-body">
                            <div className="subtitle">
                                {item.subtitle.map((sub, index) => (
                                    <div className="subtitle-item" key={index}>
                                        <span className="subtitle-upper-text">{sub.title}</span>
                                        <br />
                                        {isEdit ? (
                                            <input
                                                className="subtitle-input"
                                                value={sub.desc}
                                                onChange={(e) =>
                                                    handleEditValue(e.target.value, sub.title)
                                                }
                                            />
                                        ) : (
                                            <span className="subtitle-lower-text">
                                                {sub.desc || "—"}
                                            </span>
                                        )}
                                    </div>
                                ))}
                            </div>

                            <button className="edit-btn" onClick={toggleEdit}>
                                <span className="edit-text">{isEdit ? "Сохранить" : "Редактировать"}</span>
                            </button>
                        </div>
                    )}
                </div>
            ))}

            <ChangePassword setUser={setUser} />

            {/* Блок привязки соцсетей */}
            <div className="auth-block">
                <button className="auth-button">
                    <i className="bi bi-google" />
                    <div className="auth-text-block">
                        <span className="auth-text">Google</span>
                        <span className="auth-text">Привязать аккаунт</span>
                    </div>
                </button>
                <button className="auth-button">
                    <i className="bi bi-instagram" />
                    <div className="auth-text-block">
                        <span className="auth-text">Instagram</span>
                        <span className="auth-text">Привязать аккаунт</span>
                    </div>
                </button>
                <button className="auth-button">
                    <i className="bi bi-facebook" />
                    <div className="auth-text-block">
                        <span className="auth-text">Facebook</span>
                        <span className="auth-text">Привязать аккаунт</span>
                    </div>
                </button>
            </div>
        </div>
    );
}

function ChangePassword({ setUser }: { setUser: any }) {
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [repeatPassword, setRepeatPassword] = useState("");
    const [error, setError] = useState("");

    const handleChangePassword = async () => {
        if (!oldPassword || !newPassword || !repeatPassword) {
            setError("Заполните все поля");
            return;
        }

        if (newPassword !== repeatPassword) {
            setError("Новый пароль и повтор не совпадают");
            return;
        }

        setError("");

        try {
            const res = await fetch("http://localhost:8080/api/customers/change-password", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                credentials: "include",
                body: JSON.stringify({ oldPassword, newPassword }),
            });

            if (!res.ok) {
                const text = await res.text();
                throw new Error(text || "Ошибка при смене пароля");
            }

            alert("Пароль успешно изменён. Пожалуйста, войдите заново.");
            setUser(null);
            window.location.href = "/login";
        } catch (e: any) {
            setError(e.message || "Неизвестная ошибка");
        }
    };

    return (
        <div className="profile-item">
            <div className="profile-header">
                <span className="title">Смена пароля</span>
            </div>

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
                        placeholder="Повторите новый пароль"
                        value={repeatPassword}
                        onChange={(e) => setRepeatPassword(e.target.value)}
                    />
                </div>

                {error && <div style={{ color: "red", marginTop: 10 }}>{error}</div>}

                <button className="edit-btn" onClick={handleChangePassword}>
                    Сменить пароль
                </button>
            </div>
        </div>
    );
}
