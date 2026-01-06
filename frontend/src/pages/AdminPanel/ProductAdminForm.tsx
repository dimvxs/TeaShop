// ProductAdminForm.tsx
import React, { useState, useEffect } from 'react';
import type { ChangeEvent } from 'react';
import axios from 'axios';
import { FaSave, FaEdit, FaDownload, FaTimes } from 'react-icons/fa';

const API_BASE = 'http://localhost:8080/api';

interface FormData {
    name: string;
    brand: string;
    price: number;
    categoryIds: number[];
    newImages: File[];
    imagePreviews: string[];
    existingImageUrls: string[];
    description: string;
}

interface Category {
    id: number;
    name: string;
}

const ProductAdminForm: React.FC = () => {
    const [id, setId] = useState<string>('');
    const [formData, setFormData] = useState<FormData>({
        name: '',
        brand: '',
        price: 0,
        categoryIds: [],
        newImages: [],
        imagePreviews: [],
        existingImageUrls: [],
        description: '',
    });
    const [categories, setCategories] = useState<Category[]>([]);
    const [message, setMessage] = useState<string>('');
    const [loading, setLoading] = useState(false);

    const api = axios.create({ baseURL: API_BASE });

    // Загрузка всех категорий при монтировании компонента
    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const res = await api.get<Category[]>('/categories'); // GET /api/categories
                setCategories(res.data);
            } catch (err: any) {
                setMessage('Ошибка загрузки категорий: ' + (err.response?.data?.message || err.message));
            }
        };
        fetchCategories();
    }, []);

    const resetForm = () => {
        setFormData({
            name: '',
            brand: '',
            price: 0,
            categoryIds: [],
            newImages: [],
            imagePreviews: [],
            existingImageUrls: [],
            description: '',
        });
        setId('');
        setMessage('');
    };

    const handleGet = async () => {
        if (!id) return setMessage('Введите ID товара');
        setLoading(true);
        try {
            const res = await api.get(`/products/${id}`);
            const product = res.data;

            setFormData({
                name: product.name || '',
                brand: product.brand || '',
                price: product.price || 0,
                categoryIds: product.categories?.map((c: any) => c.id) || [],
                newImages: [],
                imagePreviews: [],
                existingImageUrls: product.imageUrls || [],
                description: product.description || 'очень вкусный чай',
            });
            setMessage('Товар загружен для редактирования');
        } catch (err: any) {
            setMessage('Ошибка: ' + (err.response?.data?.message || err.message));
        } finally {
            setLoading(false);
        }
    };

    const handleImageChange = (e: ChangeEvent<HTMLInputElement>) => {
        if (!e.target.files?.length) return;
        const files = Array.from(e.target.files);
        const previews = files.map(file => URL.createObjectURL(file));

        setFormData(prev => ({
            ...prev,
            newImages: [...prev.newImages, ...files],
            imagePreviews: [...prev.imagePreviews, ...previews],
        }));
    };

    const removeNewImage = (index: number) => {
        setFormData(prev => ({
            ...prev,
            newImages: prev.newImages.filter((_, i) => i !== index),
            imagePreviews: prev.imagePreviews.filter((_, i) => i !== index),
        }));
    };

    const removeExistingImage = (url: string) => {
        setFormData(prev => ({
            ...prev,
            existingImageUrls: prev.existingImageUrls.filter(u => u !== url),
        }));
    };

    const handleCreate = async () => {
        if (!formData.name.trim()) return setMessage('Введите название товара');
        if (!formData.brand.trim()) return setMessage('Введите бренд');
        if (formData.price < 1) return setMessage('Цена должна быть не менее 1 ');
        if (formData.categoryIds.length === 0) return setMessage('Укажите хотя бы одну категорию');

        setLoading(true);
        try {
            const data = new FormData();
            const productJson = {
                name: formData.name,
                brand: formData.brand,
                price: formData.price,
                description: formData.description,
                categoryIds: formData.categoryIds,
            };
            data.append('product', new Blob([JSON.stringify(productJson)], { type: 'application/json' }));
            formData.newImages.forEach(file => data.append('images', file));

            const res = await api.post('/products', data);
            setMessage(`Товар успешно создан! ID: ${res.data.id}`);
            resetForm();
        } catch (err: any) {
            setMessage('Ошибка создания: ' + (err.response?.data?.message || err.message));
        } finally {
            setLoading(false);
        }
    };

    const handleUpdate = async () => {
        if (!id) return setMessage('Сначала загрузите товар по ID');

        setLoading(true);
        try {
            const data = new FormData();
            const productJson = {
                name: formData.name,
                brand: formData.brand,
                price: formData.price,
                categoryIds: formData.categoryIds,
                existingImageUrls: formData.existingImageUrls,
                description: formData.description,
            };
            data.append('product', new Blob([JSON.stringify(productJson)], { type: 'application/json' }));
            formData.newImages.forEach(file => data.append('images', file));

            await api.put(`/products/${id}`, data);
            setMessage('Товар успешно обновлён!');
        } catch (err: any) {
            setMessage('Ошибка обновления: ' + (err.response?.data?.message || err.message));
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async () => {
        if (!id) return setMessage('Сначала введите ID товара для удаления');
        if (!window.confirm('Вы уверены, что хотите удалить этот товар?')) return;

        setLoading(true);
        try {
            await api.delete(`/products/${id}`);
            setMessage('Товар успешно удалён!');
            resetForm();
        } catch (err: any) {
            setMessage('Ошибка удаления: ' + (err.response?.data?.message || err.message));
        } finally {
            setLoading(false);
        }
    };

    const hasId = !!id;

    return (
        <div style={{ background: 'white', borderRadius: '12px', padding: '24px', boxShadow: '0 4px 20px rgba(0,0,0,0.08)' }}>
            <h2 style={{ marginTop: 0, color: '#2c3e50' }}>Товары</h2>

            <div style={{ marginBottom: '20px', display: 'flex', gap: '10px', alignItems: 'center' }}>
                <input
                    type="number"
                    placeholder="ID товара"
                    value={id}
                    onChange={e => setId(e.target.value)}
                    style={{ padding: '10px', borderRadius: '6px', border: '1px solid #ddd', width: '180px' }}
                />
                <button onClick={handleGet} disabled={loading} style={btnStyle('blue')}>
                    <FaDownload /> Загрузить
                </button>
            </div>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '16px', marginBottom: '20px' }}>
                <div>
                    <label style={labelStyle}>Название *</label>
                    <input style={inputStyle} type="text" value={formData.name} onChange={e => setFormData({ ...formData, name: e.target.value })} placeholder="Например: Да хун пао" />
                </div>
                <div>
                    <label style={labelStyle}>Бренд *</label>
                    <input style={inputStyle} type="text" value={formData.brand} onChange={e => setFormData({ ...formData, brand: e.target.value })} placeholder="Lovare" />
                </div>
                <div>
                    <label style={labelStyle}>Цена </label>
                    <input
                        style={inputStyle}
                        type="number"
                        min="1"
                        step="1"
                        value={formData.price}
                        onChange={e => {
                            const value = Number(e.target.value);
                            if (value >= 1 || e.target.value === '') setFormData({ ...formData, price: value || 0 });
                        }}
                        placeholder="Например: 890"
                    />
                </div>
                <div>
                    <label style={labelStyle}>Описание</label>
                    <textarea
                        style={{ ...inputStyle, height: '80px' }}
                        value={formData.description}
                        onChange={e => setFormData({ ...formData, description: e.target.value })}
                        placeholder="Например: Прекрасный чай высшего качества"
                    />
                </div>

                {/* Чекбоксы категорий */}
                <div>
                    <label style={labelStyle}>Категории *</label>
                    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
                        {categories.map(cat => (
                            <label key={cat.id} style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
                                <input
                                    type="checkbox"
                                    checked={formData.categoryIds.includes(cat.id)}
                                    onChange={e => {
                                        const checked = e.target.checked;
                                        setFormData(prev => {
                                            const ids = prev.categoryIds.slice();
                                            if (checked) ids.push(cat.id);
                                            else ids.splice(ids.indexOf(cat.id), 1);
                                            return { ...prev, categoryIds: ids };
                                        });
                                    }}
                                />
                                {cat.name}
                            </label>
                        ))}
                    </div>
                </div>
            </div>

            {/* Фото товара */}
            <div style={{ marginBottom: '24px' }}>
                <label style={labelStyle}>Фотографии товара</label>
                <div
                    style={{
                        border: '2px dashed #bdc3c7',
                        borderRadius: '8px',
                        padding: '30px',
                        textAlign: 'center',
                        background: '#f8f9fa',
                        cursor: 'pointer',
                    }}
                    onClick={() => document.getElementById('imageInput')?.click()}
                >
                    <p style={{ margin: '0 0 8px 0', color: '#2c3e50' }}>Нажмите сюда или перетащите изображения</p>
                    <p style={{ margin: 0, fontSize: '14px', color: '#7f8c8d' }}>Поддерживаются JPG, PNG, WEBP</p>
                    <input
                        id="imageInput"
                        type="file"
                        multiple
                        accept="image/*"
                        onChange={handleImageChange}
                        style={{ display: 'none' }}
                    />
                </div>

                {(formData.imagePreviews.length > 0 || formData.existingImageUrls.length > 0) && (
                    <div style={{ display: 'flex', flexWrap: 'wrap', gap: '12px', marginTop: '16px' }}>
                        {formData.existingImageUrls.map(url => (
                            <div key={url} style={{ position: 'relative' }}>
                                <img src={url} alt="existing" style={{ width: '140px', height: '140px', objectFit: 'cover', borderRadius: '8px' }} />
                                {hasId && (
                                    <button onClick={() => removeExistingImage(url)} style={removeBtnStyle}>
                                        <FaTimes size={12} />
                                    </button>
                                )}
                            </div>
                        ))}
                        {formData.imagePreviews.map((preview, i) => (
                            <div key={i} style={{ position: 'relative' }}>
                                <img src={preview} alt={`new-${i}`} style={{ width: '140px', height: '140px', objectFit: 'cover', borderRadius: '8px' }} />
                                <button onClick={() => removeNewImage(i)} style={removeBtnStyle}>
                                    <FaTimes size={12} />
                                </button>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap' }}>
                <button onClick={handleCreate} disabled={loading || hasId} style={btnStyle('green')}>
                    <FaSave /> Создать товар
                </button>
                <button onClick={handleUpdate} disabled={loading || !hasId} style={btnStyle('orange')}>
                    <FaEdit /> Обновить
                </button>
                <button onClick={handleDelete} disabled={loading || !hasId} style={btnStyle('red')}>
                    <FaTimes /> Удалить товар
                </button>
                <button onClick={resetForm} disabled={loading} style={btnStyle('gray')}>
                    Очистить форму
                </button>
            </div>

            {message && <p style={{ marginTop: '20px', color: message.includes('Ошибка') || message.includes('Введите') ? '#e74c3c' : '#27ae60', fontWeight: 'bold' }}>{message}</p>}
        </div>
    );
};

// Стили
const inputStyle = { padding: '10px', borderRadius: '6px', border: '1px solid #ddd', width: '100%', fontSize: '14px', boxSizing: 'border-box' as const };
const labelStyle = { display: 'block', fontWeight: 500, marginBottom: '6px', fontSize: '14px', color: '#2c3e50' };
const btnStyle = (color: string) => ({
    padding: '10px 16px',
    display: 'flex',
    alignItems: 'center',
    gap: '6px',
    background: color === 'blue' ? '#3498db' : color === 'green' ? '#27ae60' : color === 'orange' ? '#e67e22' : color === 'red' ? '#e74c3c' : '#7f8c8d',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    cursor: 'pointer',
    fontWeight: 'bold' as const,
});
const removeBtnStyle = { position: 'absolute', top: '-8px', right: '-8px', background: '#e74c3c', color: 'white', border: 'none', borderRadius: '50%', width: '24px', height: '24px', cursor: 'pointer' };

export default ProductAdminForm;
