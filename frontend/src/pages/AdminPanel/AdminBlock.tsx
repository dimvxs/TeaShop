import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaSave, FaEdit, FaTrash, FaDownload } from 'react-icons/fa'; // Иконки для кнопок

const API_BASE = 'http://localhost:8080/api';

interface AdminBlockProps {
    entity: string;
    displayName: string;
}

// Типы для formData (на основе твоих DTO)
type FormDataType = Partial<{
    login: string;
    password: string;
    name: string;
    lastActivity: string;
    secretWord: string;
    isSuperAdmin: boolean;
    mobile: string;
    email: string;
    bonusPoints: number;
    brand: string;
    price: number;
    categoryIds: number[];
    imageUrls: string[];
    authorId: number;
    productId: number;
    content: string;
    rating: number;
}>;

const DEFAULT_FIELDS: Record<string, FormDataType> = {
    admins: {
        login: '',
        password: '',
        name: '',
        lastActivity: new Date('2025-12-18').toISOString().split('T')[0],
        secretWord: '',
        isSuperAdmin: false,
    },
    customers: {
        login: '',
        password: '',
        name: '',
        lastActivity: new Date('2025-12-18').toISOString().split('T')[0],
        mobile: '',
        email: '',
        bonusPoints: 0,
    },
    categories: { name: '' },
    products: {
        name: '',
        brand: '',
        price: 0,
        categoryIds: [],
        imageUrls: [],
    },
    reviews: {
        authorId: 0,
        productId: 0,
        content: '',
        rating: 5,
    },
};

const AdminBlock: React.FC<AdminBlockProps> = ({ entity, displayName }) => {
    const [id, setId] = useState<string>('');
    const [data, setData] = useState<any>(null);
    const [formData, setFormData] = useState<FormDataType>({});
    const [message, setMessage] = useState<string>('');
    const [loading, setLoading] = useState(false);

    const api = axios.create({ baseURL: API_BASE });

    useEffect(() => {
        const defaults = DEFAULT_FIELDS[entity] || {};
        setFormData(defaults);
    }, [entity]);

    const validateForm = (): boolean => {
        if (entity === 'admins' || entity === 'customers') {
            if (!formData.login || !formData.password || !formData.name || !formData.email) {
                setMessage('Заполните все обязательные поля: login, password, name, email');
                return false;
            }
        }
        if (entity === 'categories' && !formData.name) {
            setMessage('Введите название категории');
            return false;
        }
        // Добавь валидацию для других сущностей по необходимости
        return true;
    };

    const handleGet = async () => {
        if (!id) return setMessage('Введите ID');
        setLoading(true);
        try {
            const res = await api.get(`/${entity}/${id}`);
            setData(res.data);
            setFormData(res.data);
            setMessage('Данные загружены успешно!');
        } catch (err: any) {
            setMessage(`Ошибка загрузки: ${err.response?.data?.message || err.message}`);
            setData(null);
        } finally {
            setLoading(false);
        }
    };

    const handleCreate = async () => {
        if (!validateForm()) return;
        setLoading(true);
        try {
            const res = await api.post(`/${entity}`, formData);
            setData(res.data);
            setId(res.data.id?.toString() || '');
            setMessage('Создано успешно! ID: ' + res.data.id);
        } catch (err: any) {
            setMessage(`Ошибка создания: ${err.response?.data?.message || err.message}`);
        } finally {
            setLoading(false);
        }
    };

    const handleUpdate = async () => {
        if (!id) return setMessage('Сначала загрузите объект');
        if (!validateForm()) return;
        setLoading(true);
        try {
            const res = await api.put(`/${entity}/${id}`, formData);
            setData(res.data);
            setMessage('Обновлено успешно!');
        } catch (err: any) {
            setMessage(`Ошибка обновления: ${err.response?.data?.message || err.message}`);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async () => {
        if (!id || !window.confirm('Точно удалить?')) return;
        setLoading(true);
        try {
            await api.delete(`/${entity}/${id}`);
            setData(null);
            setFormData(DEFAULT_FIELDS[entity] || {});
            setId('');
            setMessage('Удалено успешно!');
        } catch (err: any) {
            setMessage(`Ошибка удаления: ${err.response?.data?.message || err.message}`);
        } finally {
            setLoading(false);
        }
    };

    const handleClear = () => {
        setFormData(DEFAULT_FIELDS[entity] || {});
        setId('');
        setData(null);
        setMessage('');
    };

    const renderField = (key: string, value: any) => {
        if (key === 'id') return null;

        if (key === 'password' || key === 'secretWord') {
            return (
                <input
                    type="password"
                    value={value ?? ''}
                    onChange={(e) => setFormData({ ...formData, [key]: e.target.value })}
                    placeholder={key === 'password' ? 'Пароль (минимум 6 символов)' : 'Секретное слово'}
                />
            );
        }

        if (key === 'lastActivity') {
            return (
                <input
                    type="date"
                    value={value ?? ''}
                    onChange={(e) => setFormData({ ...formData, [key]: e.target.value })}
                />
            );
        }

        if (Array.isArray(value)) {
            const isNumberArray = key.includes('Id') || key.includes('Ids');
            return (
                <input
                    type="text"
                    placeholder="Через запятую: 1, 2, 3"
                    value={value.join(', ')}
                    onChange={(e) => {
                        const vals = e.target.value.split(',').map((s) => s.trim()).filter(Boolean);
                        setFormData({ ...formData, [key]: isNumberArray ? vals.map(Number) : vals });
                    }}
                />
            );
        }

        if (typeof value === 'number') {
            return (
                <input
                    type="number"
                    step={key.includes('price') ? '0.01' : '1'}
                    value={value}
                    onChange={(e) => setFormData({ ...formData, [key]: Number(e.target.value) || 0 })}
                />
            );
        }

        if (typeof value === 'boolean') {
            return (
                <select value={value.toString()} onChange={(e) => setFormData({ ...formData, [key]: e.target.value === 'true' })}>
                    <option value="false">Нет</option>
                    <option value="true">Да</option>
                </select>
            );
        }

        const type = key.includes('email') ? 'email' : key.includes('Date') ? 'date' : 'text';

        return (
            <input
                type={type}
                value={value ?? ''}
                onChange={(e) => setFormData({ ...formData, [key]: e.target.value })}
                placeholder={key}
            />
        );
    };

    const hasId = !!id && data;

    return (
        <div style={{ background: 'white', borderRadius: '12px', padding: '24px', boxShadow: '0 4px 20px rgba(0,0,0,0.08)' }}>
            <h2 style={{ marginTop: 0, color: '#2c3e50' }}>{displayName}</h2>

            <div style={{ marginBottom: '20px', display: 'flex', gap: '10px', alignItems: 'center' }}>
                <input style={inputStyle} type="number" placeholder="ID объекта" value={id} onChange={(e) => setId(e.target.value)} />
                <button onClick={handleGet} disabled={loading} style={btnStyle('blue')}>
                    <FaDownload /> Загрузить
                </button>
            </div>

            <div style={{ marginBottom: '20px' }}>
                <h4>{hasId ? 'Редактирование' : 'Создание нового'}</h4>
                <div style={{ display: 'grid', gap: '12px' }}>
                    {Object.entries(formData).map(([key, value]) => (
                        <div key={key}>
                            <label style={{ display: 'block', fontWeight: 500, marginBottom: '4px', fontSize: '14px' }}>{key}:</label>
                            {renderField(key, value)}
                        </div>
                    ))}
                </div>
            </div>

            <div style={{ marginBottom: '20px', display: 'flex', gap: '10px' }}>
                <button onClick={handleCreate} disabled={loading || hasId} style={btnStyle('green')}>
                    <FaSave /> Создать
                </button>
                <button onClick={handleUpdate} disabled={loading || !hasId} style={btnStyle('orange')}>
                    <FaEdit /> Обновить
                </button>
                <button onClick={handleDelete} disabled={loading || !hasId} style={btnStyle('red')}>
                    <FaTrash /> Удалить
                </button>
                <button onClick={handleClear} disabled={loading} style={btnStyle('gray')}>
                    Очистить
                </button>
            </div>

            {data && (
                <div>
                    <h4>Ответ сервера:</h4>
                    <pre style={{ background: '#f8f9fa', padding: '12px', borderRadius: '8px', fontSize: '13px', overflowX: 'auto' }}>
            {JSON.stringify(data, null, 2)}
          </pre>
                </div>
            )}

            {message && (
                <p style={{ color: message.includes('Ошибка') ? '#e74c3c' : '#27ae60', fontWeight: 'bold', marginTop: '10px' }}>
                    {message}
                </p>
            )}
        </div>
    );
};

const inputStyle = { padding: '10px', borderRadius: '6px', border: '1px solid #ddd', width: '100%', fontSize: '14px' };
const btnStyle = (color: string) => ({
    padding: '10px 16px',
    display: 'flex',
    alignItems: 'center',
    gap: '6px',
    background: color === 'blue' ? '#3498db' :
        color === 'green' ? '#27ae60' :
            color === 'orange' ? '#e67e22' :
                color === 'red' ? '#e74c3c' : '#7f8c8d',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    cursor: 'pointer',
    fontWeight: 'bold' as const,
});

export default AdminBlock;