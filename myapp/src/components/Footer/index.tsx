import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';
import {PLANET_LINK} from "@/constants";

const Footer: React.FC = () => {
  const defaultMessage = 'DUDU出品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright = {`${currentYear} ${defaultMessage}`}
      style={{
        background: 'none',
      }}
      links={[
        {
          key: 'Planet',
          title: '星雨星球',
          href: PLANET_LINK,
          blankTarget: true,
        },
        {
          key: 'github',
          title:<><GithubOutlined /> Xiaobai Du</>,
          href: 'https://github.com/AntoineDuXiaobai/',
          blankTarget: true,
        },
        {
          key: 'Youtube',
          title: '油管',
          href: 'https://youtube.com',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
