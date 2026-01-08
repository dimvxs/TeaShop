// import React, { createContext, useContext, useState, useEffect } from "react";
// import axios from "axios";
//
// const API_BASE = "http://localhost:8080/api";
//
// export type CartItem = {
//     id: number; // id ShoppingItem
//     product: {
//         id: number;
//         name: string;
//         brand?: string;
//         price: number;
//         imageUrls: string[];
//     };
//     quantity: number;
// };
//
// type CartContextType = {
//     items: CartItem[];
//     addToCart: (productId: number, quantity: number) => Promise<void>;
//     updateQuantity: (itemId: number, quantity: number) => Promise<void>;
//     removeFromCart: (itemId: number) => Promise<void>;
//     loadCart: () => Promise<void>;
//     totalItems: number;
//     totalPrice: number;
//     loading: boolean;
// };
//
//
// const CartContext = createContext<CartContextType | undefined>(undefined);
//
// export const CartProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
//     const [items, setItems] = useState<CartItem[]>([]);
//     const [loading, setLoading] = useState(true);
//
//
//     const loadCart = async () => {
//         try {
//             const res = await axios.get(`${API_BASE}/cart`, { withCredentials: true });
//             const data = res.data; // ShoppingCartDTO
//
//             const items: CartItem[] = data.items.map((i: any) => ({
//                 id: i.id,
//                 product: {
//                     id: i.productId,
//                     name: i.productName,
//                     brand: "", // пока нет, можно потом добавить в DTO
//                     price: i.pricePerUnit,
//                     imageUrls: ["/images/default-product.jpg"], // сюда можно вставить реальные пути из продукта
//                 },
//                 quantity: i.quantity,
//             }));
//
//             setItems(items);
//         } catch (err) {
//             console.error("Ошибка загрузки корзины:", err);
//             setItems([]);
//         } finally {
//             setLoading(false);
//         }
//     };
//
//
//
//     useEffect(() => {
//         loadCart();
//     }, []);
//
//     const addToCart = async (productId: number, quantity: number) => {
//         try {
//             await axios.post(`${API_BASE}/cart/add`, { productId, quantity }, { withCredentials: true });
//             await loadCart();
//         } catch (err) {
//             console.error("Ошибка добавления в корзину:", err);
//         }
//     };
//
//
//     const updateQuantity = async (itemId: number, quantity: number) => {
//         if (quantity <= 0) {
//             await removeFromCart(itemId);
//             return;
//         }
//         try {
//             await axios.put(`${API_BASE}/cart/update-item`, { itemId, quantity }, { withCredentials: true });
//             await loadCart();
//         } catch (err) {
//             console.error("Ошибка обновления количества:", err);
//         }
//     };
//
//     const removeFromCart = async (itemId: number) => {
//         try {
//             await axios.delete(`${API_BASE}/cart/remove-item/${itemId}`, { withCredentials: true });
//             await loadCart();
//         } catch (err) {
//             console.error("Ошибка удаления из корзины:", err);
//         }
//     };
//
//
//     const totalItems = items.reduce((sum, item) => sum + item.quantity, 0);
//     const totalPrice = items.reduce((sum, item) => sum + item.product.price * item.quantity, 0);
//
//     return (
//         <CartContext.Provider
//             value={{
//                 items,
//                 addToCart,
//                 updateQuantity,
//                 removeFromCart,
//                 loadCart,
//                 totalItems,
//                 totalPrice,
//                 loading,
//             }}
//         >
//             {children}
//         </CartContext.Provider>
//     );
// };
//
// export const useCart = () => {
//     const context = useContext(CartContext);
//     if (!context) throw new Error("useCart must be used within CartProvider");
//     return context;
// };

import React, { createContext, useContext, useState, useEffect } from "react";
import axios, { AxiosError } from "axios";

const API_BASE = "http://localhost:8080/api";

export type CartItem = {
    id: number; // ShoppingItem id
    product: {
        id: number;
        name: string;
        brand?: string;
        price: number;
        imageUrls: string[];
    };
    quantity: number;
};

type CartContextType = {
    items: CartItem[];
    addToCart: (productId: number, quantity: number) => Promise<void>;
    updateQuantity: (itemId: number, quantity: number) => Promise<void>;
    removeFromCart: (itemId: number) => Promise<void>;
    loadCart: () => Promise<void>;
    totalItems: number;
    totalPrice: number;
    loading: boolean;
};

const CartContext = createContext<CartContextType | undefined>(undefined);

export const CartProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [items, setItems] = useState<CartItem[]>([]);
    const [loading, setLoading] = useState(true);

    // ------------------ Загрузка корзины ------------------
    const loadCart = async () => {
        setLoading(true);
        try {
            const res = await axios.get(`${API_BASE}/cart`, { withCredentials: true });
            const data = res.data; // ShoppingCartDTO с бекенда

            const mappedItems: CartItem[] = data.items.map((i: any) => ({
                id: i.id,
                product: {
                    id: i.productId,
                    name: i.productName,
                    brand: i.productBrand || "",
                    price: i.pricePerUnit,
                    imageUrls: i.productImageUrls?.length ? i.productImageUrls : ["/images/default-product.jpg"],
                },
                quantity: i.quantity,
            }));

            setItems(mappedItems);
        } catch (err) {
            console.error("Ошибка загрузки корзины:", err);
            setItems([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadCart();
    }, []);

    // ------------------ Добавление в корзину ------------------
    const addToCart = async (productId: number, quantity: number) => {
        try {
            const payload = { productId, quantity };
            console.log("Добавляем в корзину:", payload);

            await axios.post(`${API_BASE}/cart/add`, payload, { withCredentials: true });
            await loadCart();
        } catch (err) {
            const error = err as AxiosError;
            console.error("Ошибка добавления в корзину:", error.response?.data || error.message);
        }
    };

    // ------------------ Обновление количества ------------------
    const updateQuantity = async (itemId: number, quantity: number) => {
        if (quantity <= 0) {
            await removeFromCart(itemId);
            return;
        }
        try {
            await axios.put(`${API_BASE}/cart/update-item`, { itemId, quantity }, { withCredentials: true });
            await loadCart();
        } catch (err) {
            const error = err as AxiosError;
            console.error("Ошибка обновления количества:", error.response?.data || error.message);
        }
    };

    // ------------------ Удаление из корзины ------------------
    const removeFromCart = async (itemId: number) => {
        try {
            await axios.delete(`${API_BASE}/cart/remove-item/${itemId}`, { withCredentials: true });
            await loadCart();
        } catch (err) {
            const error = err as AxiosError;
            console.error("Ошибка удаления из корзины:", error.response?.data || error.message);
        }
    };

    const totalItems = items.reduce((sum, item) => sum + item.quantity, 0);
    const totalPrice = items.reduce((sum, item) => sum + item.product.price * item.quantity, 0);

    return (
        <CartContext.Provider
            value={{
                items,
                addToCart,
                updateQuantity,
                removeFromCart,
                loadCart,
                totalItems,
                totalPrice,
                loading,
            }}
        >
            {children}
        </CartContext.Provider>
    );
};

export const useCart = () => {
    const context = useContext(CartContext);
    if (!context) throw new Error("useCart must be used within CartProvider");
    return context;
};
