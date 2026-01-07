import React, { useState } from "react";
import "./ProfilePage.css";

export default function ProfilePage() {
    const [open, setOpen] = useState<number | null>(null);
    const [isEdit, setIsEdit] = useState(false);
    

    type Subtitle = {
        id: number;
        title: string;
        desc: string;
    }

    type Item = {
        id: number;
        title: string;
        subtitle?: Subtitle[];
    };
    

    const Sitems: Item[] = [
        { id: 1, title: "Мой аккаунт", subtitle: [{ id: 1, title: "Телефон", desc: "+1234567890" }, { id: 2, title: "Фамилия", desc: "Неуказано" }, { id: 3, title: "Имя", desc: "Неуказано" }] },
        { id: 2, title: "Персональные данные", subtitle: [{ id: 1, title: "Дата рождения", desc: "Неуказано" }, { id: 2, title: "Пол", desc: "Неуказано" }, { id: 3, title: "Язык", desc: "Русский" }] },
        { id: 3, title: "Мои заказчики" },
        { id: 4, title: "Контакты", subtitle: [{ id: 1, title: "Почта", desc: "someemail@gmail.com" }] },
        { id: 5, title: "Адресс доставки" },
        {
            id: 6,
            title: "Дополнительная информация",
        },
    ];

    const [items, setItems] = useState<Item[]>(Sitems);

    const editItemValue = (text: string, itemId: number, subtId: number) => {
        setItems(prev => 
            prev.map(item =>
                item.id === itemId
                    ? {
                        ...item,
                        subtitle: item.subtitle?.map(sub =>
                            sub.id === subtId
                                ? { ...sub, desc: text }
                                : sub
                        ),
                    }
                :item
            )
        )

    }

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
                                        {isEdit ? (
                                            <input className="subtitle-input" value={subt.desc} onChange={e => editItemValue(e.target.value, item.id, subt.id)} autoFocus />
                                        ) : (
                                            <span className="subtitle-lower-text">{subt.desc}</span>
                                        )}
                                    </div>
                                ))}
                            </div>
                            <button className="edit-btn">
                                <span className="edit-text" onClick={() => setIsEdit(!isEdit)}>{isEdit ? "Сохранить" : "Редактировать"}</span>
                            </button>
                        </div>
                    )}
                </div>
            ))}
        </div>
    );

}