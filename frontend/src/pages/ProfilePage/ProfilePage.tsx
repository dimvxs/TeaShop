import React, { useState } from "react";
import "./ProfilePage.css";

export default function ProfilePage() {
    const [open, setOpen] = useState<number | null>(null);


    type Subtitle = {
        title: string;
        desc: string;
    }

    type Item = {
        id: number;
        title: string;
        subtitle?: Subtitle[];
    };
    

    const items: Item[] = [
        { id: 1, title: "Мой аккаунт", subtitle: [{ title: "Телефон", desc: "+1234567890" }, { title: "Фамилия", desc: "Неуказано" }, { title: "Имя", desc: "Неуказано" }] },
        { id: 2, title: "Персональные данные", subtitle: [{ title: "Дата рождения", desc: "Неуказано" }, { title: "Пол", desc: "Неуказано" }, { title: "Язык", desc: "Русский" }] },
        { id: 3, title: "Мои заказчики" },
        { id: 4, title: "Контакты", subtitle: [{ title: "Почта", desc: "someemail@gmail.com" }] },
        { id: 5, title: "Адресс доставки" },
        {
            id: 6,
            title: "Дополнительная информация",
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
                        <span className={open === item.id ? "arrow open" : "arrow"}>⌄</span>
                    </button>

                    {open === item.id && (
                        <div className="profile-body">
                            <div className="subtitle">
                                {item.subtitle?.map((subt) => (
                                    <div>
                                        <span className="subtitle-upper-text">{subt.title}</span>
                                        <br></br>
                                        <span className="subtitle-lower-text">{subt.desc}</span> 
                                    </div>
                                ))}
                            </div>
                            <button className="edit-btn">
                                <span className="edit-text">Редактировать</span>
                            </button>
                        </div>
                    )}
                </div>
            ))}
        </div>
    );

}