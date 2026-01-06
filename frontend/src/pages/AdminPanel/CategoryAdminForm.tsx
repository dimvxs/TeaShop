import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { FaTrash, FaSave, FaSync } from 'react-icons/fa';

const API_BASE = 'http://localhost:8080/api';

interface Category {
    id: number;
    name: string;
}

const CategoryAdminForm: React.FC = () => {
    const [name, setName] = useState('');
    const [categories, setCategories] = useState<Category[]>([]);
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const api = axios.create({ baseURL: API_BASE });

    // ===== загрузка категорий =====
    const loadCategories = async () => {
        setLoading(true);
        try {
            const res = await api.get('/categories');
            setCategories(res.data);
        } catch (err: any) {
            setMessage('Ошибка загрузки категорий');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadCategories();
    }, []);

    // ===== создание =====
    const handleCreate = async () => {
        if (!name.trim()) {
            setMessage('Введите название категории');
            return;
        }

        setLoading(true);
        try {
            await api.post('/categories', { name });
            setName('');
            setMessage('Категория создана');
            loadCategories();
        } catch (err: any) {
            setMessage(err.response?.data?.message || 'Ошибка создания');
        } finally {
            setLoading(false);
        }
    };

    // ===== удаление =====
    const handleDelete = async (id: number) => {
        if (!window.confirm(`Удалить категорию ID ${id}?`)) return;

        setLoading(true);
        try {
            await api.delete(`/categories/${id}`);
            setMessage(`Категория ${id} удалена`);
            loadCategories();
        } catch {
            setMessage('Ошибка удаления');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={container}>
            <h2>Категории</h2>

            {/* ===== создание ===== */}
            <div style={card}>
                <h4>Добавить категорию</h4>
                <input
                    style={input}
                    placeholder="Название категории"
                    value={name}
                    onChange={e => setName(e.target.value)}
                />
                <button onClick={handleCreate} disabled={loading} style={btn('green')}>
                    <FaSave /> Создать
                </button>
            </div>

            {/* ===== список ===== */}
            <div style={card}>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <h4>Список категорий</h4>

                </div>

                <table style={table}>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    {categories.map(cat => (
                        <tr key={cat.id}>
                            <td>{cat.id}</td>
                            <td>{cat.name}</td>
                            <td>
                                <button
                                    onClick={() => handleDelete(cat.id)}
                                    style={btn('red')}
                                >
                                    <FaTrash />
                                </button>
                            </td>
                        </tr>
                    ))}
                    {categories.length === 0 && (
                        <tr>
                            <td colSpan={3} style={{ textAlign: 'center', color: '#777' }}>
                                Категорий нет
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>

            {message && <p style={{ fontWeight: 'bold' }}>{message}</p>}
        </div>
    );
};

export default CategoryAdminForm;

/* ===== стили ===== */

const container = {
    background: '#fff',
    padding: '24px',
    borderRadius: '12px',
    boxShadow: '0 4px 20px rgba(0,0,0,0.08)',
};

const card = {
    marginBottom: '24px',
};

const input = {
    padding: '10px',
    width: '100%',
    marginBottom: '10px',
    borderRadius: '6px',
    border: '1px solid #ddd',
};

const btn = (color: string) => ({
    padding: '8px 12px',
    border: 'none',
    borderRadius: '6px',
    cursor: 'pointer',
    background:
        color === 'green' ? '#27ae60' :
            color === 'red' ? '#e74c3c' :
                '#3498db',
    color: '#fff',
});

const table = {
    width: '100%',
    borderCollapse: 'collapse' as const,
};

