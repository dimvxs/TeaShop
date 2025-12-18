import React from 'react';
import AdminBlock from './AdminBlock';

const AdminPanel: React.FC = () => {
    return (
        <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
            <h1>Админ-панель GoldenLeaf Shop</h1>

            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '40px' }}>
                <AdminBlock entity="admins" displayName="Админы" />
                <AdminBlock entity="customers" displayName="Клиенты" />
                <AdminBlock entity="categories" displayName="Категории" />
                <AdminBlock entity="products" displayName="Товары" />
                <AdminBlock entity="reviews" displayName="Отзывы" />
            </div>
        </div>
    );
};

export default AdminPanel;