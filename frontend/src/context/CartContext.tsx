// import React, { createContext, useContext, useState, useEffect } from "react";
// import axios, { AxiosError } from "axios";
//
// const API_BASE = "http://localhost:8080/api";
//
// export type CartItem = {
//     id: number; // ShoppingItem id
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
// const CartContext = createContext<CartContextType | undefined>(undefined);
//
// export const CartProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
//     const [items, setItems] = useState<CartItem[]>([]);
//     const [loading, setLoading] = useState(true);
//
//     // ------------------ Загрузка корзины ------------------
//     const loadCart = async () => {
//         setLoading(true);
//         try {
//             const res = await axios.get(`${API_BASE}/cart`, { withCredentials: true });
//             const data = res.data;
//
//             const itemsArray = data.items || data.cartItems || [];
//
//             const mappedItems: CartItem[] = itemsArray.map((i: any) => {
//                 const productName = i.productName || "Без названия";
//                 const productId = i.productId || 0;
//                 const price = i.pricePerUnit || 0;
//
//                 let imageUrls: string[] = ["/images/default-product.jpg"];
//                 if (i.imageUrl) {
//                     imageUrls = [`http://localhost:8080${i.imageUrl}`];
//                 }
//
//                 return {
//                     id: i.id,
//                     quantity: i.quantity || 1,
//                     product: {
//                         id: productId,
//                         name: productName,
//                         brand: i.productBrand || "",
//                         price: price,
//                         imageUrls,
//                     },
//                 };
//             });
//
//             setItems(mappedItems);
//         } catch (err) {
//             console.error("Ошибка загрузки корзины:", err);
//             setItems([]);
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     useEffect(() => {
//         loadCart();
//     }, []);
//
//     // ------------------ Добавление в корзину ------------------
//     const addToCart = async (productId: number, quantity: number) => {
//         try {
//             const payload = { productId, quantity };
//             console.log("Добавляем в корзину:", payload);
//
//             await axios.post(`${API_BASE}/cart/add`, payload, { withCredentials: true });
//             await loadCart();
//         } catch (err) {
//             const error = err as AxiosError;
//             console.error("Ошибка добавления в корзину:", error.response?.data || error.message);
//         }
//     };
//
//     // ------------------ Обновление количества ------------------
//     const updateQuantity = async (itemId: number, quantity: number) => {
//         if (!itemId) {
//             console.error("updateQuantity: itemId is null или undefined");
//             return;
//         }
//
//         if (quantity <= 0) {
//             await removeFromCart(itemId);
//             return;
//         }
//
//         try {
//             await axios.put(`${API_BASE}/cart/update-item`, { itemId, quantity }, { withCredentials: true });
//             await loadCart();
//         } catch (err) {
//             const error = err as AxiosError;
//             console.error("Ошибка обновления количества:", error.response?.data || error.message);
//         }
//     };
//
//     // ------------------ Удаление из корзины ------------------
//     const removeFromCart = async (itemId: number) => {
//         if (!itemId) {
//             console.error("removeFromCart: itemId is null или undefined");
//             return;
//         }
//         try {
//             await axios.delete(`${API_BASE}/cart/remove-item/${itemId}`, { withCredentials: true });
//             await loadCart();
//         } catch (err) {
//             const error = err as AxiosError;
//             console.error("Ошибка удаления из корзины:", error.response?.data || error.message);
//         }
//     };
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
//
//
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
    incrementItem: (itemId: number) => Promise<void>;
    decrementItem: (itemId: number) => Promise<void>;
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
            const data = res.data;

            const itemsArray = data.items || data.cartItems || [];

            const mappedItems: CartItem[] = itemsArray.map((i: any) => {
                const productName = i.productName || "Без названия";
                const productId = i.productId || 0;
                const price = i.pricePerUnit || 0;

                let imageUrls: string[] = ["/images/default-product.jpg"];
                if (i.imageUrl) {
                    imageUrls = [`http://localhost:8080${i.imageUrl}`];
                }

                return {
                    id: i.id,
                    quantity: i.quantity || 1,
                    product: {
                        id: productId,
                        name: productName,
                        brand: i.productBrand || "",
                        price: price,
                        imageUrls,
                    },
                };
            });

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
        if (!itemId) {
            console.error("updateQuantity: itemId is null или undefined");
            return;
        }

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

    // ------------------ Методы увеличения/уменьшения ------------------
    const incrementItem = async (itemId: number) => {
        const item = items.find(i => i.id === itemId);
        if (!item) return;
        await updateQuantity(itemId, item.quantity + 1);
    };

    const decrementItem = async (itemId: number) => {
        const item = items.find(i => i.id === itemId);
        if (!item) return;
        const newQuantity = item.quantity - 1;
        await updateQuantity(itemId, newQuantity);
    };

    // ------------------ Удаление из корзины ------------------
    const removeFromCart = async (itemId: number) => {
        if (!itemId) {
            console.error("removeFromCart: itemId is null или undefined");
            return;
        }
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
                incrementItem,
                decrementItem,
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