import React from "react";
import type { ReactNode } from "react";

import Header from "../../components/Header/Header";
import Footer from "../../components/Footer/Footer";

interface DefaultLayoutProps {
  children: ReactNode;
}

const DefaultLayout: React.FC<DefaultLayoutProps & { headerSticky?: boolean }> = ({
  children,
  headerSticky = true,
}) => {
  return (
    <>
      <Header sticky={headerSticky} />
      <main>{children}</main>
      <Footer />
    </>
  );
};


export default DefaultLayout;
